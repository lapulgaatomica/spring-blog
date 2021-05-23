package com.project.blog.services;

import com.project.blog.domain.BlogUser;
import com.project.blog.dtos.AuthenticationResponse;
import com.project.blog.dtos.LoginRequest;
import com.project.blog.dtos.RegisterRequest;
import com.project.blog.repositories.UserRepository;
import com.project.blog.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public String register(RegisterRequest registerRequest) {
        // Todo verify that email is valid

        boolean usernameAlreadyExists = userRepository.findByUsername(registerRequest.getUsername()).isPresent();
        boolean emailAlreadyExists = userRepository.findByEmail(registerRequest.getEmail()).isPresent();

        if(usernameAlreadyExists || emailAlreadyExists){
            throw new IllegalStateException("Username or Email is already taken");
        }

        BlogUser user = new BlogUser();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return user.getEmail();
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
}
