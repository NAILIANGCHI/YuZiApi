package com.naraci.core.util;


import com.naraci.app.domain.SysUser;
import com.naraci.core.aop.CustomException;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Component
public class RedisUtils {
    // 存储的token
    public static final String token = "token";
    // 使用Redis模拟存储Token

    @Resource
    public  RedisTemplate<String, SysUser> redisTemplate;

    // 存入token
    public  String addToken(SysUser sysUser, String token) {
        // 将用户id和uuidToken 存入redis
        redisTemplate.opsForValue().set(token, sysUser, Duration.ofDays(30));
        return token;
    }

    // 验证token
    public boolean checkToken(String id, String token) {
     SysUser user = (SysUser) redisTemplate.opsForValue().get(token);
     if (user == null) {
         throw new CustomException("token失效");
     }
     return true;
    }

    // token 内容获取
    public String requestToken(String token) {
        int index = token.indexOf('_'); // 找到下划线的位置
        if (index != -1) { // 如果找到了下划线
            String result = token.substring(0, index); // 截取从0到下划线位置的子字符串
            if (!result.equals("login")) {
                throw new CustomException("token不合法");
            }

            SysUser sysUser = redisTemplate.opsForValue().get(token);
            if (sysUser == null) {
                throw new CustomException("登录失效请重新登录");
            }

            return sysUser.getId();
        }else {
            throw new CustomException("token不合法");
        }
    }
}
