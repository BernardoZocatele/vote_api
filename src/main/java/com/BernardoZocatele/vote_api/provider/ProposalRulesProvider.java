package com.BernardoZocatele.vote_api.provider;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProposalRulesProvider {

    private final List<ProposalRules> proposalRules = List.of(
        new ProposalRules(
            dto -> dto.start_date().isBefore(LocalDateTime.now()),
            "Start date can't be before the creation date!"
        ),
        new ProposalRules(
            dto -> dto.expiration_date().isBefore(dto.start_date()), 
            "Expiration date can't be before the start date")
    );

    public List<ProposalRules> getProposalRules() {
        return proposalRules;
    }
}
