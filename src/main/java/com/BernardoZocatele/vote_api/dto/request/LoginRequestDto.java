package com.BernardoZocatele.vote_api.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
    @NotNull(message = "Cpf is mandatory")
    String cpf, 

    @NotNull(message = "Password is mandatory")
    String password
) {}
