package com.example.demo.security.jwt;

import com.example.demo.entity.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * token工具类
 */
public class JwtUtils {

    @Value("${simplecrud.app.jwtSecret}")
    private String jwtSecret;

    @Value("${simplecrud.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    //生成一个token
    public String generateJwtToken(Authentication authentication) {
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userInfo.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.ES256,jwtSecret)
                .compact();
    }

    //通过token获得用户名
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //检验token
    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("token错误");
        }

        return false;
    }
}
