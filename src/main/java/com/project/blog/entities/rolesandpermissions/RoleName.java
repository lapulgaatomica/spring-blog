package com.project.blog.entities.rolesandpermissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    USER,
    POST_MODERATOR,
    COMMENT_MODERATOR,
    SUPER_ADMIN;
}
