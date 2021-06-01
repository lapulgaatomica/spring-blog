package com.project.blog.entities.rolesandpermissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BlogUserPermission {

    POST_WRITE("post:write"),
    COMMENT_WRITE("comment:write"),
    USER_WRITE("user:write");

    private final String permission;
}
