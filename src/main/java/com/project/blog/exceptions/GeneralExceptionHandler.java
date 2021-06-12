package com.project.blog.exceptions;

import com.project.blog.payloads.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntryNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> entryNotFoundException(EntryNotFoundException e){
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({InsufficientPermissionException.class})
    @ResponseBody
    public ResponseEntity<Object> insufficientPermissionException(InsufficientPermissionException e){
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
