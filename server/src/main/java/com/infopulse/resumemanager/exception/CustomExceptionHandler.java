package com.infopulse.resumemanager.exception;

import com.infopulse.resumemanager.dto.apierror.ApiErrorResponse;
import com.infopulse.resumemanager.dto.apierror.ApiSubError;
import com.infopulse.resumemanager.dto.apierror.ApiValidationError;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Read <a href="https://www.toptal.com/java/spring-boot-rest-api-error-handling">
 *     Guide to Spring Boot REST API Error Handling</a> to have a clear understanding of what I'm doing.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handle(UsernameNotFoundException exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND,
                "User not found exception.",
                exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiErrorResponse> handle(BadCredentialsException exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Incorrect username or password",
                exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiErrorResponse);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> handle(UserAlreadyExistsException exception) {
        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.CONFLICT,
                "Username is already exists",
                exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<ApiErrorResponse> handle(ExpiredJwtException exception) {
        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Token is expired",
                exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiErrorResponse> handle(AccessDeniedException exception) {
        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.FORBIDDEN,
                "AccessDeniedException",
                exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<ApiSubError> apiValidationErrors = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> apiValidationErrors.add(
                        new ApiValidationError(
                                fieldError.getObjectName(),
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        ))
                );
        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(objectError -> apiValidationErrors.add(
                        ApiValidationError.of(
                                objectError.getObjectName(),
                                objectError.getDefaultMessage()
                        ))
                );

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                ex.getLocalizedMessage(),
                apiValidationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (!ex.getMessage().contains("Role")) {
            super.handleHttpMessageNotReadable(ex, headers, status, request);
        }
        String message = "HttpMessageNotReadableException.class";
        if(ex.getLocalizedMessage().contains("Role")){
            message = "Role does not exist.";
        }
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON request",
                message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }


}
