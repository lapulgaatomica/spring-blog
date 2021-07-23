package com.project.blog.services;

import com.project.blog.payloads.*;
import com.project.blog.entities.Role;

import java.util.List;

public interface UserService {
    GenericResponse register(RegistrationRequest user);
    List<Role> getRoles();
    GenericResponse changeRole(String username, ChangeRoleRequest changeRoleRequest);
    GenericResponse changePassword(Long id, PasswordChangeRequest request, String nameOfCurrentlyLoggedInUser);
    GenericResponse generatePasswordResetToken(String email);
    GenericResponse resetPassword(String token);
    GenericResponse confirmPasswordReset(PasswordResetRequest request, String token);
}
