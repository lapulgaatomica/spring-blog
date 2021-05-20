package com.project.blog.services;

import com.project.blog.dtos.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public String register(UserDTO user) {
        return "done";
    }
}
