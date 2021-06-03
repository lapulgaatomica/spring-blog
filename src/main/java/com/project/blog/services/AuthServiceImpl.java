package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.dtos.RegistrationRequest;
import com.project.blog.entities.rolesandpermissions.Role;
import com.project.blog.entities.rolesandpermissions.RoleName;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.project.blog.entities.rolesandpermissions.RoleName.*;

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

        List<Role> roles = new ArrayList<>();

        if(blogUserRepository.count() == 0){
            if(roleRepository.count() == 0){
                roleRepository.saveAll(List.of(new Role(USER), new Role(COMMENT_MODERATOR), new Role(POST_MODERATOR), new Role(SUPER_ADMIN)));
            }
            roles.add(roleRepository.findByName(SUPER_ADMIN).get());
        }else{
            roles.add(roleRepository.findByName(USER).get());
        }
        user.setRoles(roles);
        blogUserRepository.save(user);
        return user.getEmail();
    }
}
