package org.example.kcbtechtest.exception;


import org.example.kcbtechtest.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDetails> projectNotFound(ProjectNotFoundException e) {
        return new ResponseEntity<>(getErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDetails> taskNotFound(TaskNotFoundException e) {
        return new ResponseEntity<>(getErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorDetails> internalServerError(HttpServerErrorException.InternalServerError e) {
        return new ResponseEntity<>(getErrorDetails(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorDetails getErrorDetails(Exception e) {
        return ErrorDetails.builder()
                .errorType(ErrorType.ERROR)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
