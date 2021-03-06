package com.project.blog.configuration;

import com.project.blog.security.JwtConfigProperties;
import com.project.blog.security.JwtTokenVerifier;
import com.project.blog.security.JwtUsernameAndPasswordAuthenticationFilter;
import com.project.blog.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SecretKey secretKey;
    private final JwtConfigProperties jwtConfigProperties;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, SecretKey secretKey, JwtConfigProperties jwtConfigProperties) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.secretKey = secretKey;
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtUsernameAndPasswordAuthenticationFilter())
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfigProperties), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/users/register")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/**/password/**")
                .permitAll()
                .antMatchers( "/api/v1/users/password/**")
                .permitAll()
                .antMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter() throws Exception {
        /* Could have just passed new JwtUsernameAndPasswordAuthenticationFilter(
        authenticationManager(), jwtConfigProperties, secretKey) to addFilter() in the configure method but
         I wouldn't have been able to set filter processor Url there*/
         JwtUsernameAndPasswordAuthenticationFilter filter = new JwtUsernameAndPasswordAuthenticationFilter(
                authenticationManager(), jwtConfigProperties, secretKey);
        filter.setFilterProcessesUrl("/api/v1/users/login");
        return filter;
    }
}
