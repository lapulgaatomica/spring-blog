package com.project.blog.services;

import com.project.blog.domain.BlogUser;
import com.project.blog.dtos.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public String register(RegisterRequest registerRequest) {
        // Todo verify that email is valid
        BlogUser user = userDetailsService.signUpUser(
                new BlogUser(registerRequest.getUsername(),
                        registerRequest.getEmail(),
                        registerRequest.getPassword())
        );

        return user.getEmail();
    }
}
