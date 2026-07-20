package com.BernardoZocatele.vote_api.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProposalRequestDto(
    @NotBlank(message = "Tittle mandatory.")
    String title, 
    
    @NotBlank(message = "Description mandatory.")
    String description, 
    
    @NotNull(message = "Start date mandatory.")
    LocalDateTime start_date, 
    
    @NotNull(message = "Expiration date mandatory.")
    LocalDateTime expiration_date

) {}

