package org.sang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWTUtils {
    private final static String KEY="123456";
    private final static String USER_KEY="user";
    // 过期时间
    private final static long EXPIRE_TIME=1000*60*60*24;
    public static String createJWT(Long userId) {
        return JWT.create()
                .withClaim(USER_KEY,userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME)) // 设置过期时间
                .sign(Algorithm.HMAC256(KEY));
    }

    public static Long getUserId(String jwt) {
        return JWT.decode(jwt).getClaim(USER_KEY).asLong();
    }
}
