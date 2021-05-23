package com.project.blog.services;

import com.project.blog.dtos.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest user);
}
