package com.example.jwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     *
     * @param userName
     * @param isRememberMe 是否记住我，来选择使用过期时间
     * @return
     */
    public static   String createToken(String userName, boolean isRememberMe){
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        return Jwts.builder()
                //设置加密方式和字符串
                .signWith(SignatureAlgorithm.HS512,SECRET)
                //设置发行者
                .setIssuer(ISS)
                //设置主题
                .setSubject(userName)
                //设置生效时间
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                //简介方式
                .compact();
    }

    /**
     * 从token中获取用户名
     */

    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 是否已过期
     */

    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 通过token获取内容
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
