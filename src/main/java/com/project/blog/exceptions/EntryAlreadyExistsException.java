package com.project.blog.exceptions;

public class EntryAlreadyExistsException extends RuntimeException{

    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
