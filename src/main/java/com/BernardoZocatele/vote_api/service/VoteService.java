package com.BernardoZocatele.vote_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.BernardoZocatele.vote_api.dto.request.VoteRequestDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.entity.Votes;
import com.BernardoZocatele.vote_api.infra.exception.ProposalNotFoundException;
import com.BernardoZocatele.vote_api.infra.exception.UserNotFoundException;
import com.BernardoZocatele.vote_api.provider.vote.VoteRulesProvider;
import com.BernardoZocatele.vote_api.repository.ProposalRepository;
import com.BernardoZocatele.vote_api.repository.UserRepository;
import com.BernardoZocatele.vote_api.repository.VoteRepository;

@Service
public class VoteService {

    private final VoteRulesProvider voteRulesProvider;
    private final UserRepository userRepository;
    private final ProposalRepository proposalRepository;
    private final VoteRepository voteRepository;

    public VoteService(VoteRulesProvider voteRulesProvider, UserRepository userRepository, ProposalRepository proposalRepository, VoteRepository voteRepository) {
        this.voteRulesProvider = voteRulesProvider;
        this.userRepository = userRepository;
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
    }
    
    public List<String> checkVote(VoteRequestDto request, Long userId) {
        if(userRepository.existsById(userId) == false) {
            throw new UserNotFoundException();
        }

        if(proposalRepository.existsById(request.proposal_id()) == false) {
            throw new ProposalNotFoundException();
        } 
        
        return voteRulesProvider.getVoteRules().stream()
                                .filter(dto -> dto.isValid(request))
                                .map(dto -> dto.errorMessage())
                                .toList();
    }

    public Votes createVote(VoteRequestDto request, Long userId) {
        Votes newVote = new Votes();
        User user = userRepository.findUserById(userId);
        Proposal proposal = proposalRepository.findProposalById(request.proposal_id());

        newVote.setUser(user);
        newVote.setProposal(proposal);
        newVote.setVote(request.vote());
        newVote.setCreation_date(LocalDateTime.now());

        voteRepository.save(newVote);

        return newVote;
    }
}
