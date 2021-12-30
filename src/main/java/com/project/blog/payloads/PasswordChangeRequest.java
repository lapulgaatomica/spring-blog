package com.project.blog.payloads;

import java.util.Objects;

//@EqualsAndHashCode
public class PasswordChangeRequest {
    public String oldPassword, newPassword1, newPassword2;

    public PasswordChangeRequest(String oldPassword, String newPassword1, String newPassword2) {
        this.oldPassword = oldPassword;
        this.newPassword1 = newPassword1;
        this.newPassword2 = newPassword2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordChangeRequest that = (PasswordChangeRequest) o;
        return Objects.equals(oldPassword, that.oldPassword) && Objects.equals(newPassword1, that.newPassword1) && Objects.equals(newPassword2, that.newPassword2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword1, newPassword2);
    }
}
