package com.project.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;
}
