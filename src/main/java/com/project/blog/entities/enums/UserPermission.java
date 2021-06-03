package com.project.blog.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {

    POST_WRITE("post:write"),
    COMMENT_WRITE("comment:write"),
    USER_WRITE("user:write");

    private final String permission;
}
