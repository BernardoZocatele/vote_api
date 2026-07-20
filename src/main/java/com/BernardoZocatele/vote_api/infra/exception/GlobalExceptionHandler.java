package com.BernardoZocatele.vote_api.infra.exception;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalErrorMessage> userNotFoundException(UserNotFoundException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ProposalNotFoundException.class)
    public ResponseEntity<GlobalErrorMessage> proposalNotFoundException(ProposalNotFoundException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GlobalErrorMessage> illegalStateException(IllegalStateException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GlobalErrorMessage> accessDeniedException(AccessDeniedException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalErrorMessage> badCredentialsException(BadCredentialsException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<GlobalErrorMessage> internalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalErrorMessage> runtimeException(RuntimeException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(RegisterUserException.class)
    public ResponseEntity<GlobalErrorMessage> registerUserException(RegisterUserException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNPROCESSABLE_CONTENT, " Empty mandatory field.");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(threatResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.BAD_REQUEST, "Invalid request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorMessage> handleGenericException(Exception exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error server.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
