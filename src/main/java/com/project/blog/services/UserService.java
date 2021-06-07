package com.project.blog.services;

import com.project.blog.payloads.ChangeRoleRequest;
import com.project.blog.payloads.RegistrationRequest;
import com.project.blog.entities.Role;

import java.util.List;

public interface UserService {
    String register(RegistrationRequest user);
    List<Role> getRoles();
    String changeRole(String username, ChangeRoleRequest changeRoleRequest);
}
