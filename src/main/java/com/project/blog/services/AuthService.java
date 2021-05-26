package com.project.blog.services;

import com.project.blog.dtos.RegistrationRequest;

public interface AuthService {
    String register(RegistrationRequest user);
}
