package com.project.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LoginRequest {
    @NotNull
    @NotEmpty
    public String username, password;
}
