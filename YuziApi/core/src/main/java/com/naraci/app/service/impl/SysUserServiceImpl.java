package com.naraci.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naraci.app.domain.SysUser;
import com.naraci.app.entity.response.LoginResponse;
import com.naraci.app.entity.reuqest.LoginRequest;
import com.naraci.app.entity.reuqest.SysUserRegisterRequest;
import com.naraci.app.service.SysUserService;
import com.naraci.app.mapper.SysUserMapper;
import com.naraci.core.aop.CustomException;
import com.naraci.core.entity.UserInfo;
import com.naraci.core.util.RandomUtils;
import com.naraci.core.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
* @author Zhaoyu
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2024-02-15 11:35:14
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Resource
    private RedisTemplate<String, SysUser> redisTemplate;
    @Resource
    private RedisTemplate<String, String> redisTemplateCode;
    //获取邮件发送类
    @Resource
    JavaMailSender javaMailSender;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtils redisUtils;
    @Override
    public void register(SysUserRegisterRequest request) {

        // 判断邮箱
        SysUser sysUserEmail = sysUserMapper.selectOne(
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(SysUser::getEmail, request.getEmail())
        );

        if (!ObjectUtils.isEmpty(sysUserEmail)) {
            throw new CustomException("该邮箱已被注册");
        }

        SysUser sysUser = new SysUser();

        sysUser.setEmail(request.getEmail());
        sysUser.setName(request.getName());
        sysUser.setPassword(request.getPassword());
        sysUser.setGender(request.getGender());
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void sendMessage(UserInfo userInfo) {
        String emailAddress = userInfo.getEmail();
        //初始化邮件信息类
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("YuZiTool注册");
        simpleMailMessage.setFrom("naraci@email.naraci.top");
        simpleMailMessage.setTo(emailAddress);
        //获取验证码
        String verification = RandomUtils.generateCode();
        //将验证码存放进邮箱
        simpleMailMessage.setText("这是您的验证码"+verification + "请在5分钟内注册完成！");
        //获取redis操作类
        ValueOperations<String, String> valueOperations = redisTemplateCode.opsForValue();
        javaMailSender.send(simpleMailMessage);
        /*设置缓存*/
        valueOperations.set(emailAddress,verification);
        /**
         * K key, final long timeout, final TimeUnit unit
         * key 存储数据的key值
         * TimeUnit 时间单位
         * timeout 数据的过期时间
         * */
        redisTemplate.expire(emailAddress,60*5, TimeUnit.SECONDS);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        SysUser user = sysUserMapper.selectOne(
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(SysUser::getEmail, request.getEmail()));
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该邮箱未注册！");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new CustomException("密码错误！");
        }
        String token = "login_" + UUID.randomUUID() + user.getId();
        redisUtils.addToken(user, token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(user);
        loginResponse.setToken(token);
        return loginResponse;
    }

}




