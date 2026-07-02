package com.BernardoZocatele.vote_api.provider.vote;

import java.util.function.Predicate;

import com.BernardoZocatele.vote_api.dto.request.VoteRequestDto;

public record VoteRules(Predicate<VoteRequestDto> validator, String errorMessage) {   
    public boolean isValid(VoteRequestDto request) {
        return validator.test(request);
    }
}
