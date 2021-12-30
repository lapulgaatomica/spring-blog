package com.project.blog.entities.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleName {
    USER(Set.of()),
    COMMENT_MODERATOR(Set.of(UserPermission.COMMENT_WRITE)),
    POST_MODERATOR(Set.of(UserPermission.POST_WRITE)),
    SUPER_ADMIN(Set.of(UserPermission.POST_WRITE, UserPermission.COMMENT_WRITE, UserPermission.USER_WRITE));

    private final Set<UserPermission> permissions;

    RoleName(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
