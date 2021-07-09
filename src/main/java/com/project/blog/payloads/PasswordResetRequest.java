package com.project.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PasswordResetRequest {
    public String password1, password2;
}
