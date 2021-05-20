package com.project.blog.controllers;

import com.project.blog.dtos.UserDTO;
import com.project.blog.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO user){
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.register(user));
    }
}
