package com.project.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    @Value("${jwt.signing.key}")
    private String signingKey;

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        return Jwts
                .builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwt){
        parser().setSigningKey(signingKey).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
