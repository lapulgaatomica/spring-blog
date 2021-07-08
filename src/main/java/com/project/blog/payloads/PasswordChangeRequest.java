package com.project.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PasswordChangeRequest {
    public String oldPassword, newPassword1, newPassword2;
}
