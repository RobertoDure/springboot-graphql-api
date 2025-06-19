package com.ie.graphql.api.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }

    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s with id %d not found", resourceType, id), "RESOURCE_NOT_FOUND");
    }

    public ResourceNotFoundException(String resourceType, String identifier) {
        super(String.format("%s with identifier %s not found", resourceType, identifier), "RESOURCE_NOT_FOUND");
    }
}
