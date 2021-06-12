package com.project.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordChangeRequest {
    public String oldPassword, newPassword1, newPassword2;
}
