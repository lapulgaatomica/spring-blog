package com.project.blog.payloads;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"success", "message"})
public class LoginResponse {
    private Boolean success;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(success, that.success) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, token);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
