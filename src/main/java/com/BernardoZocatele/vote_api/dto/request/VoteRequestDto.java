package com.BernardoZocatele.vote_api.dto.request;

import com.BernardoZocatele.vote_api.entity.VoteEnum;

public record VoteRequestDto(Long user_id, Long proposal_id, VoteEnum vote) {
    
}
