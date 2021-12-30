package com.project.blog.entities.enums;

public enum UserPermission {
    POST_WRITE("post:write"),
    COMMENT_WRITE("comment:write"),
    USER_WRITE("user:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
