package com.naraci.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author ShenZhaoYu
 * @date 2024/2/29
 */
@Component
public class JwtUtils {
    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.expireTime}")
    private long time;

    public static final String TOKEN = "token";



    // 生成jwt代码
    public String createToken(Map<String, Object> claims) {

        return JWT.create()
                .withClaim("claims", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + time)) // 设置过期时间
                .sign(Algorithm.HMAC256(jwtKey)); // 指定密钥
    }

    // 验证 token
    public Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtKey))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    /**
     * 获取token值
     */

    public void responseToken() {

    }
}
