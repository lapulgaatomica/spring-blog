package com.project.blog.services;

import com.project.blog.dtos.AuthenticationResponse;
import com.project.blog.dtos.LoginRequest;
import com.project.blog.dtos.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest user);
    AuthenticationResponse login(LoginRequest loginRequest);
}
