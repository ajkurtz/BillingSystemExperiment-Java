package com.andykurtz.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ServerErrorException.class)
    ResponseEntity exceptionHandler(ServerErrorException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity exceptionHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity exceptionHandler(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorMessage(exception.getMessage()));
    }

    private Object buildErrorMessage(Object errors) {
        return Collections.singletonMap("message", errors);
    }
}
