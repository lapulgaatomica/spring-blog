package com.project.blog.configuration;

import com.project.blog.security.JwtConfigProperties;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {
    private final JwtConfigProperties jwtConfigProperties;

    public JwtSecretKey(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @Bean//Bean that injects the signing key for the JWT token(I know, tautology)
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtConfigProperties.getSecretKey().getBytes());
    }
}
