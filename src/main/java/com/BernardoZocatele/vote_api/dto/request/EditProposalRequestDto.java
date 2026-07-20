package com.BernardoZocatele.vote_api.dto.request;

import java.time.LocalDateTime;

public record EditProposalRequestDto(String title, String description, LocalDateTime start_date, LocalDateTime expiration_date) {
    
}
