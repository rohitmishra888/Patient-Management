package org.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
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

    public void validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch (SignatureException e){
            throw new JwtException("Invalid signature");
        }
        catch (JwtException e){
            throw new JwtException("Invalid token");
        }
    }
}
