package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.dtos.RegistrationRequest;
import com.project.blog.entities.rolesandpermissions.RoleName;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final BlogUserRepository blogUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        // Todo verify that email is valid

        boolean usernameAlreadyExists = blogUserRepository.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailAlreadyExists = blogUserRepository.findByEmail(registrationRequest.getEmail()).isPresent();

        if(usernameAlreadyExists || emailAlreadyExists){
            throw new IllegalStateException("Username or Email is already taken");
        }

        BlogUser user = new BlogUser();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        user.setRoles(List.of(roleRepository.findByName(RoleName.USER).get()));

        blogUserRepository.save(user);

        return user.getEmail();
    }
}
