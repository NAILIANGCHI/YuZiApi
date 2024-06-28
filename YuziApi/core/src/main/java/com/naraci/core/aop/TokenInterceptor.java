package com.naraci.core.aop;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.naraci.app.domain.UserRole;
import com.naraci.app.mapper.UserRoleMapper;
import com.naraci.app.domain.Role;
import com.naraci.app.domain.SysUser;
import com.naraci.app.entity.enums.RoleEnum;
import com.naraci.app.mapper.RoleMapper;
import com.naraci.app.mapper.SysUserMapper;
import com.naraci.core.annotation.AccessPostType;
import com.naraci.core.entity.UserInfo;
import com.naraci.core.util.JsonUtil;
import com.naraci.core.util.JwtUtils;
import com.naraci.core.util.ThreadLocalUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 * 验证token
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 本线程存储计时器
     */
    private static final ThreadLocal<StopWatch> TIME = new ThreadLocal<>();
    private static final ThreadLocal<StringBuilder> LOG_INFO = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // PreFlight请求，忽略本拦截器
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        // 开始计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 计时器放在本线程中
        TIME.set(stopWatch);

        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取请求头部
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>(16);
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                headerMap.put(key, request.getHeader(key));
            }
        }
        // 生成请求id
        String requestId = String.valueOf(UUID.randomUUID()).replaceAll("-","");
        // 请求id放在request的域中
        request.setAttribute("requestId", requestId);
        // 请求id放在log上下文中
        MDC.put("requestId", requestId);
        // 准备日志信息
        StringBuilder sb = new StringBuilder();
        sb.append("\n【request_id】:").append(requestId);
        sb.append("\n【请求 URL】:").append(request.getRequestURL());
//        sb.append("\n【请求 IP】:").append(IpUtil.getIp(request));
        sb.append("\n【请求Header】:").append(JsonUtil.toJson(headerMap));
        sb.append("\n【请求参数】:").append(JsonUtil.toJson(parameterMap));
        // 日志信息存放在本线程中
        LOG_INFO.set(sb);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> clazz = handlerMethod.getBeanType();
        Method method = handlerMethod.getMethod();

        // 令牌验证
        String token = request.getHeader("token");
        UserInfo userInfo = new UserInfo();
        SysUser user = new SysUser();
        try {
            Map<String, Object> claims = jwtUtils.parseToken(token);
            ThreadLocalUtils.set(claims);
            if (ObjectUtils.isEmpty(claims)) {
                throw new CustomException("token验证失败");
            }
             SysUser sysUser = sysUserMapper.selectById((Serializable) claims.get("id"));
            if (ObjectUtils.isEmpty(sysUser)) {
                throw new CustomException("用户错误，请重新登录后重试!");
            }
            UserRole userRole = userRoleMapper.selectByUserid(sysUser.getId());
            if (ObjectUtils.isEmpty(userRole)) {
                throw new CustomException("该账户没有角色 操作失败");
            }
            Role role = roleMapper.selectId(userRole.getRoleId());
            if (ObjectUtils.isEmpty(role)) {
                throw new CustomException("没有角色信息请联系管理员添加");
            }
            BeanUtils.copyProperties(sysUser, user);
            userInfo.setSysUser(user);
            userInfo.setRole(role);
            // 对jwt 解析存入userinfo
            request.setAttribute(JwtUtils.TOKEN, userInfo);

            // 添加角色权限控制
            if (!clazz.isAnnotationPresent(AccessPostType.class) && !method.isAnnotationPresent(AccessPostType.class)) {
                return true;
            }

            if (RoleEnum.RoleEnums.Admin.equals(userInfo.getRole().getRole())) {
                return true;
            }

            AccessPostType accessPostType = handlerMethod.getMethod().getAnnotation(AccessPostType.class);
            if (accessPostType == null) {
                accessPostType = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AccessPostType.class);
            }

            if (!Arrays.stream(accessPostType.value()).toList()
                    .contains(userInfo.getRole().getRole())) {
                throw new CustomException(402, "该无此接口权限(岗位)");
            }

            return true; //放行
        } catch (Exception e) {
            response.setStatus(401);
            throw new CustomException("登录失效");
//            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 在请求全部完成之后
     * @param request   请求
     * @param response  响应
     * @param handler   handler
     * @param ex        异常
     * @throws Exception    异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空ThreadLocal
        ThreadLocalUtils.remove();
        if (!CorsUtils.isPreFlightRequest(request)) {
            this.printLog(request);
        }
        response.setHeader("Access-Control-Expose-Headers", "token");
    }

    /**
     * 打印日志
     * @param request
     */
    private void printLog(HttpServletRequest request) {
        // 取出计时器
        StopWatch stopWatch = TIME.get();
        // 计时器停止
        stopWatch.stop();
        // 取计时器耗时
        long time = stopWatch.getTotalTimeMillis();

        // 取出日志信息
        StringBuilder localLog = LOG_INFO.get();

        // 日志拼接接口耗时
        localLog.append("\n【接口耗时】:").append(time).append("ms");

        // 从request取aop打印的log信息
        StringBuilder logInfo = (StringBuilder) request.getAttribute("log");
        if (logInfo != null) {
            localLog.append(logInfo);
        }
        // 打印请求日志
        log.info(localLog.toString());
        this.clear();
    }

    /**
     * 清除变量
     */
    private void clear() {
        // 计时器线程清除
        TIME.remove();
        // 日志信息线程清除
        LOG_INFO.remove();
        // 清除log上下文信息
        MDC.clear();
    }

}
