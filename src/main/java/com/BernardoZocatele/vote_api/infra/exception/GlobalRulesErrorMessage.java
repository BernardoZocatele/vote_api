package com.BernardoZocatele.vote_api.infra.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class GlobalRulesErrorMessage {
    private HttpStatus status;
    private List<String> message;
    
}