package com.BernardoZocatele.vote_api.provider.vote;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.BernardoZocatele.vote_api.repository.ProposalRepository;
import com.BernardoZocatele.vote_api.repository.VoteRepository;

@Component
public class VoteRulesProvider {

    private VoteRepository voteRepository;
    private ProposalRepository proposalRepository;

    public VoteRulesProvider(VoteRepository voteRepository, ProposalRepository proposalRepository) {
        this.voteRepository = voteRepository;
        this.proposalRepository = proposalRepository;
    }

    private final List<VoteRules> voteRules = List.of(
        new VoteRules(
            dto -> voteRepository.existsByUserIdAndProposalId(dto.user_id(), dto.proposal_id()),
            "User can only vote one time in a proposal."
        ),
        new VoteRules(
            dto -> proposalRepository.findExpirationDateById(dto.proposal_id()).isBefore(LocalDateTime.now()), 
            "Proposal is already closed."
        )
    );

    public List<VoteRules> getVoteRules() {
        return voteRules;
    }
}
