package com.BernardoZocatele.vote_api.dto.request;

import com.BernardoZocatele.vote_api.entity.VoteEnum;

public record VoteRequestDto(Long proposal_id, VoteEnum vote) {
    
}
