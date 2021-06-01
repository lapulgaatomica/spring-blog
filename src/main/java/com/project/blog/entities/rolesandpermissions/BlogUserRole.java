package com.project.blog.entities.rolesandpermissions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.project.blog.entities.rolesandpermissions.BlogUserPermission.*;

@AllArgsConstructor
@Getter
public enum BlogUserRole {
    USER(Set.of(null)),
    POST_MODERATOR(Set.of(POST_WRITE)),
    COMMENT_MODERATOR(Set.of(COMMENT_WRITE)),
    SUPER_ADMIN(Set.of(POST_WRITE, COMMENT_WRITE, USER_WRITE));

    private final Set<BlogUserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
