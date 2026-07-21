package org.pm.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;
    String secret = "ahnZC9Ae2j7leQN7gIan0lGtmpHGHHVc7ID7IFdDBbW=";
    //@Value("${jwt.secret}") String secret
    public JwtUtil(){
        byte[] keyBytes = Base64.getDecoder().decode(this.secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role){
        return Jwts.builder().subject(email)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10)) //10 hours
                .signWith(secretKey)
                .compact();
    }


}
