package com.ie.graphql.api.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom GraphQL error handler to convert exceptions into GraphQL errors.
 * This handler maps specific exceptions to appropriate GraphQL error types and messages.
 */
@Component
public class CustomGraphQLErrorHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        ErrorType errorType = ErrorType.INTERNAL_ERROR;
        String message = "An unexpected error occurred. Please try again later.";
        Map<String, Object> extensions = new HashMap<>();

        if (ex instanceof ResourceNotFoundException) {
            errorType = ErrorType.NOT_FOUND;
            message = ex.getMessage();
            extensions.put("errorCode", ((ResourceNotFoundException) ex).getErrorCode());
        } else if (ex instanceof ValidationException) {
            errorType = ErrorType.BAD_REQUEST;
            message = ex.getMessage();
            extensions.put("errorCode", ((ValidationException) ex).getErrorCode());
        } else if (ex instanceof ConflictException) {
            errorType = ErrorType.BAD_REQUEST;
            message = ex.getMessage();
            extensions.put("errorCode", ((ConflictException) ex).getErrorCode());
        } else if (ex instanceof ServiceException) {
            errorType = ErrorType.INTERNAL_ERROR;
            message = ex.getMessage();
            extensions.put("errorCode", ((ServiceException) ex).getErrorCode());
        }

        return GraphqlErrorBuilder.newError()
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .errorType(errorType)
                .extensions(extensions)
                .build();
    }
}
