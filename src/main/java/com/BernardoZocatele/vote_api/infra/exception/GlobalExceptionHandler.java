package com.BernardoZocatele.vote_api.infra.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<GlobalErrorMessage> userNotFoundException(UserNotFoundException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ProposalNotFoundException.class)
    private ResponseEntity<GlobalErrorMessage> proposalNotFoundException(ProposalNotFoundException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<GlobalErrorMessage> illegalStateException(IllegalStateException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<GlobalErrorMessage> accessDeniedException(AccessDeniedException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<GlobalErrorMessage> badCredentialsException(BadCredentialsException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    private ResponseEntity<GlobalErrorMessage> internalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<GlobalErrorMessage> runtimeException(RuntimeException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<GlobalErrorMessage> nullPointerException(NullPointerException exception) {
        GlobalErrorMessage threatResponse = new GlobalErrorMessage(HttpStatus.UNAUTHORIZED, "Authentication error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    
    
}
