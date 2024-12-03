package org.example.kcbtechtest.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(final String message) {
        super(message);
    }

    public ProjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFoundException(final Throwable cause) {
        super(cause);
    }
}
