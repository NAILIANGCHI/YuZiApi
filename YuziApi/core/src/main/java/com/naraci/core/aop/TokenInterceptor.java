package com.naraci.core.aop;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.naraci.app.domain.SysUser;
import com.naraci.app.mapper.SysUserMapper;
import com.naraci.core.entity.UserInfo;
import com.naraci.core.util.JsonUtil;
import com.naraci.core.util.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 * 验证token
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtils redisUtils;

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

        UserInfo userInfo = new UserInfo();
        String token = request.getHeader("token");
        if (token == null) {
            throw new CustomException("未登录！");
        }
        String userId  = redisUtils.requestToken(token);
        SysUser sysUser = sysUserMapper.selectOne(
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(SysUser::getId, userId)
        );
        BeanUtils.copyProperties(sysUser, userInfo);
        request.setAttribute(RedisUtils.token, userInfo);
        return true;
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
