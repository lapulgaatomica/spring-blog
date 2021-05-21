package com.project.blog.services;

import com.project.blog.domain.BlogUser;
import com.project.blog.dtos.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public String register(UserDTO userDTO) {
        // Todo verify that email is valid
        BlogUser user = userDetailsService.signUpUser(
                new BlogUser(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword())
        );

        return user.getEmail();
    }
}
