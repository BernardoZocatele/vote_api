package com.BernardoZocatele.vote_api.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;
import com.BernardoZocatele.vote_api.dto.request.EditProposalRequestDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.provider.proposal.ProposalRulesProvider;
import com.BernardoZocatele.vote_api.repository.ProposalRepository;
import com.BernardoZocatele.vote_api.repository.UserRepository;
import com.BernardoZocatele.vote_api.repository.VoteRepository;


@Service
public class ProposalService {

    private final ProposalRulesProvider proposalRulesProvider;
    private final UserRepository userRepository;
    private final ProposalRepository proposalRepository;
    private final VoteRepository voteRepository;

    public ProposalService(ProposalRulesProvider proposalRulesProvider, UserRepository userRepository, ProposalRepository proposalRepository, VoteRepository voteRepository) {
        this.proposalRulesProvider = proposalRulesProvider;
        this.userRepository = userRepository;
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
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

    @Transactional
    public List<String> editProposal(Long id, EditProposalRequestDto dto) throws Exception {
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());

        User user = userRepository.findUserById(dto.user_id());

        if(!proposal.getUser().equals(user)) {
            throw new AccessDeniedException("User can't edit this proposal.");
        }

        if(voteRepository.existsByProposalId(id)) {
            throw new IllegalStateException("User can't edit this proposal because someone already voted.");
        }

        CreateProposalRequestDto newProposal = new CreateProposalRequestDto(
            dto.title(),
            dto.description(),
            dto.start_date(),
            dto.expiration_date(),
            dto.user_id()
        );

        List<String> errors = checkProposal(newProposal);

        if(errors.isEmpty()) {
            proposal.setTitle(dto.title());
            proposal.setDescription(dto.description());
            proposal.setStart_date(dto.start_date());
            proposal.setExpiration_date(dto.expiration_date());
        }

        return errors;
    }
}
