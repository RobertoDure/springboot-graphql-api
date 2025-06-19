package com.ie.graphql.api.exception;

/**
 * Exception thrown when input validation fails.
 */
public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
    }

    public ValidationException(String field, String message) {
        super(String.format("Validation failed for field '%s': %s", field, message), "VALIDATION_ERROR");
    }
}
