package com.project.blog.services;

import com.project.blog.payloads.*;
import com.project.blog.entities.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface UserService {
    GenericResponse register(RegistrationRequest user);
    List<Role> getRoles(Authentication authentication);
    GenericResponse changeRole(String username, ChangeRoleRequest changeRoleRequest, Authentication authentication);
    GenericResponse changePassword(Long id, PasswordChangeRequest request, Authentication authentication);
    GenericResponse generatePasswordResetToken(String email);
    GenericResponse resetPassword(String token);
    GenericResponse confirmPasswordReset(PasswordResetRequest request, String token);
}
