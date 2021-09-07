package com.project.blog.exceptions;

public class PasswordMismatchException extends RuntimeException{

    public PasswordMismatchException(String message) {
        super(message);
    }

}
