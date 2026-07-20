package com.BernardoZocatele.vote_api.dto.response;

import org.springframework.http.HttpStatus;

public record SuccessResponseDto(HttpStatus status, String message) {
    
}
