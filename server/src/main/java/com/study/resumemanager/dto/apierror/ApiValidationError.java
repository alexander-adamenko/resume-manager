package com.study.resumemanager.dto.apierror;

public record ApiValidationError(
        String object,
        String field,
        String message
) implements ApiSubError {
    public static ApiValidationError of(
            String object,
            String message
    ) {
        return new ApiValidationError(
                object,
                null,
                message
        );
    }
}
