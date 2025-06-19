package com.ie.graphql.api.exception;

/**
 * Base exception class for service layer exceptions.
 * This exception is intended to be extended by more specific exception types.
 */
public class ServiceException extends RuntimeException {

    private final String errorCode;

    public ServiceException(String message) {
        super(message);
        this.errorCode = "GENERAL_SERVICE_ERROR";
    }

    public ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GENERAL_SERVICE_ERROR";
    }

    public ServiceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
