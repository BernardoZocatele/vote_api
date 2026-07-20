package com.BernardoZocatele.vote_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
    @NotBlank(message = "Name is mandatory.")
    String name,
    
    @NotBlank(message = "Cpf is mandatory")
    String cpf,
    
    @NotBlank(message = "Password is mandatory")
    String password
) {}
