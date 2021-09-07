package com.project.blog.exceptions;

public class InsufficientPermissionException extends RuntimeException{

    public InsufficientPermissionException(String message) {
        super(message);
    }

}
