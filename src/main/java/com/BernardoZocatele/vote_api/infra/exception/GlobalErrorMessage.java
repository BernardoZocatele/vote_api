package com.BernardoZocatele.vote_api.infra.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class GlobalErrorMessage {
    private HttpStatus status;
    private String message;
    
}
