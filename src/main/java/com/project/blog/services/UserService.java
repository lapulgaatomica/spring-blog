package com.project.blog.services;

import com.project.blog.dtos.RegistrationRequest;
import com.project.blog.entities.Role;

import java.util.List;

public interface UserService {
    String register(RegistrationRequest user);
    List<Role> getRoles();
}
