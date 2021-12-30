package com.project.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.payloads.GenericResponse;
import com.project.blog.payloads.LoginRequest;
import com.project.blog.payloads.LoginResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfigProperties jwtConfigProperties;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfigProperties jwtConfigProperties, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfigProperties = jwtConfigProperties;
        this.secretKey = secretKey;
    }

//    @SneakyThrows
    /* method annotated with @SneakyThrows because response.getOutputStream() throws IOException that couldn't be thrown
     by the method declaration because it wasn't thrown in the overridden method*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        }catch (Exception e){
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), new GenericResponse(false, e.getMessage()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException{
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Timestamp.valueOf(LocalDateTime.now().plusHours(
                        jwtConfigProperties.getTokenExpirationAfterDays() * 24)))
                .signWith(secretKey)
                .compact();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        new ObjectMapper().writeValue(response.getOutputStream(),
                new LoginResponse(true, jwtConfigProperties.getTokenPrefix() + token));
    }
}
