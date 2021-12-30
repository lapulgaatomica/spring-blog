package com.project.blog.payloads;

import com.project.blog.entities.enums.RoleName;

import java.util.Objects;

public class ChangeRoleRequest {
    private RoleName role;

    public ChangeRoleRequest() {
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeRoleRequest that = (ChangeRoleRequest) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    @Override
    public String toString() {
        return "ChangeRoleRequest{" +
                "role=" + role +
                '}';
    }
}
