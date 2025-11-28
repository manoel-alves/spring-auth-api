package com.manoel.springauthapi.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(Locale locale) {
        String message = messageSource.getMessage("user.notfound", new Object[]{}, locale);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("user.notfound", message));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(Locale locale) {
        String message = messageSource.getMessage("user.password.invalid", new Object[]{}, locale);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError("user.password.invalid", message));
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleUserNotFound(EmailAlreadyRegisteredException ex, Locale locale) {
        String message = messageSource.getMessage("user.email.registered", new Object[]{ex.getMessage()}, locale);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError("user.email.registered", message));
    }

    // DTO
    public record ApiError(String code, String message) {}
}
