package org.example.kcbtechtest.exception;

import lombok.Builder;
import lombok.Data;
import org.example.kcbtechtest.enums.ErrorType;

import java.time.LocalDateTime;


@Data
@Builder
public class ErrorDetails {
    private String message;
    private LocalDateTime timestamp;
    private ErrorType errorType;
}
