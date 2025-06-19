package com.ie.graphql.api.exception;

/**
 * Exception thrown when there's a conflict in the business logic.
 * For example, when trying to create a resource that already exists.
 */
public class ConflictException extends ServiceException {

    public ConflictException(String message) {
        super(message, "CONFLICT_ERROR");
    }

    public ConflictException(String resourceType, String identifier, String reason) {
        super(String.format("Conflict with %s '%s': %s", resourceType, identifier, reason), "CONFLICT_ERROR");
    }
}
