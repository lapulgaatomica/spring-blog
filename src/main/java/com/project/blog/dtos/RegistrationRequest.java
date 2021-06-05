package com.project.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RegistrationRequest {
    @NotNull
    @NotEmpty
    private String username, email, password;
}
