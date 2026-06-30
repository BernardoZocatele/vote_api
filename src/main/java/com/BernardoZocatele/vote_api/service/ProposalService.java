package com.BernardoZocatele.vote_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.provider.ProposalRulesProvider;
import com.BernardoZocatele.vote_api.repository.ProposalRepository;
import com.BernardoZocatele.vote_api.repository.UserRepository;

@Service
public class ProposalService {

    private final ProposalRulesProvider proposalRulesProvider;
    private final UserRepository userRepository;
    private final ProposalRepository proposalRepository;

    public ProposalService(ProposalRulesProvider proposalRulesProvider, UserRepository userRepository, ProposalRepository proposalRepository) {
        this.proposalRulesProvider = proposalRulesProvider;
        this.userRepository = userRepository;
        this.proposalRepository = proposalRepository;
    }

    public List<String> checkProposal(CreateProposalRequestDto dto) {

        if(userRepository.existsById(dto.user_id()) == false) return List.of("User not found");

        return proposalRulesProvider.getProposalRules().stream()
                                    .filter(rule -> rule.isValid(dto))
                                    .map(rule -> rule.errorMessage())
                                    .toList();
    }

    public Proposal createProposal(CreateProposalRequestDto dto) {
        Proposal newProposal = new Proposal();
        User user = userRepository.findUserById(dto.user_id());

        newProposal.setTitle(dto.title());
        newProposal.setDescription(dto.description());
        newProposal.setCreation_date(LocalDateTime.now());
        newProposal.setStart_date(dto.start_date());
        newProposal.setExpiration_date(dto.expiration_date());
        newProposal.setUser(user);

        proposalRepository.save(newProposal);

        return newProposal;
    }
    
}
