package com.project.blog.services;

import com.project.blog.dtos.RegisterRequest;

public interface RegistrationService {
    String register(RegisterRequest user);
}
