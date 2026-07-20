package com.BernardoZocatele.vote_api.provider.vote;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.BernardoZocatele.vote_api.entity.User;
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

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private final List<VoteRules> voteRules = List.of(
        new VoteRules(
            dto ->  {
                User userLogado = getAuthenticatedUser();
                return voteRepository.existsByUserIdAndProposalId(userLogado.getId(), dto.proposal_id());
            },
                "User can only vote one time in a proposal."
        ),
        new VoteRules(
            dto -> proposalRepository.findExpirationDateById(dto.proposal_id()).isBefore(LocalDateTime.now()), 
            "Proposal is already closed."
        ),
        new VoteRules(
            dto -> proposalRepository.findStartDateById(dto.proposal_id()).isAfter(LocalDateTime.now()), 
            "Proposal is not open yet."
        )
    );

    public List<VoteRules> getVoteRules() {
        return voteRules;
    }
}
