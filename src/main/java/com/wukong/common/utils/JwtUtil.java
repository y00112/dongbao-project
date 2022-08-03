package com.wukong.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    // secret
    private static final String secret = "abcdefg";

    //生成一个token
    public static String createToken(String subject){

        String token = Jwts.builder().setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))   //设置token过期时间
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
        return token;
    }

    //解析token
    public static String parseToken(String token){

        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String subject = body.getSubject();

        return subject;

    }

    public static void main(String[] args) {
        String name = "悟空";
        String token = createToken(name);
        System.out.println("token：" +token);

        //解析token

        String str = parseToken(token);
        System.out.println("解析Token："+str);

        //睡4s token过期
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str11 = parseToken(token);
        System.out.println("解析Token："+str11);

    }
}
