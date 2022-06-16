package com.study.resumemanager.dto.apierror;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * You may read <a href="https://blogs.oracle.com/javamagazine/post/records-come-to-java">
 *     this</a> as I did.
 *     There's another <a href="https://blogs.oracle.com/javamagazine/post/diving-into-java-records-serialization-marshaling-and-bean-state-validation">
 *     article</a> I wish to read in the future.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        LocalDateTime timestamp,
        HttpStatus status,
        String message,
        String debugMessage,
        List<ApiSubError> subErrors
) {
    public static ApiErrorResponse of(
            HttpStatus status,
            String debugMessage
    ){
        return new ApiErrorResponse(
                LocalDateTime.now(),
                status,
                "Unexpected error",
                debugMessage,
                null
        );
    }

    public static ApiErrorResponse of(
            HttpStatus status,
            String message,
            String debugMessage) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                status,
                message,
                debugMessage,
                null
        );
    }
    public static ApiErrorResponse of(
            HttpStatus status,
            String message,
            String debugMessage,
            List<ApiSubError> subErrors) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                status,
                message,
                debugMessage,
                subErrors
        );
    }

}
