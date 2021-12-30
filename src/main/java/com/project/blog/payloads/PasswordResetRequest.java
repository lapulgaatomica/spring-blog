package com.project.blog.payloads;

import java.util.Objects;

//@EqualsAndHashCode
public class PasswordResetRequest {
    public String password1, password2;

    public PasswordResetRequest(String password1, String password2) {
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetRequest that = (PasswordResetRequest) o;
        return Objects.equals(password1, that.password1) && Objects.equals(password2, that.password2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password1, password2);
    }
}
