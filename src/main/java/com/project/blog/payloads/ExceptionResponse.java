package com.project.blog.payloads;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime date;

    public ExceptionResponse(String message, HttpStatus status, LocalDateTime date) {
        this.message = message;
        this.status = status;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
