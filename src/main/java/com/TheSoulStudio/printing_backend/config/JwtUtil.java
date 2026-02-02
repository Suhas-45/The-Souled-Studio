package com.TheSoulStudio.printing_backend.config;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class JwtUtil {
    private final Key key;
    private final Long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        byte [] decodeKey = Base64.getDecoder().decode(secret);
        this.key = hmacShaKeyFor(decodeKey);
        this.expiration = expiration;
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("TheSoulStudio")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

    public boolean validate(String token){
        try{
            parseClaims(token);
            return true;
        }catch (ExpiredJwtException e){
            System.out.println("Token Expired!!!");
        }catch (JwtException e){
            System.out.println("Invalid Token");
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
