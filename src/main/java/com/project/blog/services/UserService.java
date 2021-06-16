package com.project.blog.services;

import com.project.blog.payloads.*;
import com.project.blog.entities.Role;

import java.util.List;

public interface UserService {
    String register(RegistrationRequest user);
    List<Role> getRoles();
    String changeRole(String username, ChangeRoleRequest changeRoleRequest);
    GenericResponse changePassword(Long id, PasswordChangeRequest request);
    GenericResponse generatePasswordResetToken(String email);
    GenericResponse requestResetPassword(String token);
    GenericResponse resetPassword(PasswordResetRequest request, String token);
}
