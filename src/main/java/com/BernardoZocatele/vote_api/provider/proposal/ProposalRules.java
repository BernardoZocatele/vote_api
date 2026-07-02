package com.BernardoZocatele.vote_api.provider.proposal;

import java.util.function.Predicate;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;

public record ProposalRules(Predicate<CreateProposalRequestDto> validator, String errorMessage) {
    public boolean isValid(CreateProposalRequestDto request) {
        return validator.test(request);
    }
}
