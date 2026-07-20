package com.BernardoZocatele.vote_api.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;
import com.BernardoZocatele.vote_api.dto.request.EditProposalRequestDto;
import com.BernardoZocatele.vote_api.dto.response.VotesResponseDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.entity.VoteEnum;
import com.BernardoZocatele.vote_api.infra.exception.ProposalNotFoundException;
import com.BernardoZocatele.vote_api.infra.exception.UserNotFoundException;
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

    public List<String> checkProposal(CreateProposalRequestDto dto, Long userId) {

        if(userRepository.existsById(userId) == false) {
            throw new UserNotFoundException();
        }

        return proposalRulesProvider.getProposalRules().stream()
                                    .filter(rule -> rule.isValid(dto))
                                    .map(rule -> rule.errorMessage())
                                    .toList();
    }

    public Proposal createProposal(CreateProposalRequestDto dto, Long userId) {
        Proposal newProposal = new Proposal();
        User user = userRepository.findUserById(userId);

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
    public List<String> editProposal(Long id, EditProposalRequestDto dto, Long userId) throws Exception {
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new ProposalNotFoundException());

        User user = userRepository.findUserById(userId);

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
            dto.expiration_date()
        );

        List<String> errors = checkProposal(newProposal, userId);

        if(errors.isEmpty()) {
            proposal.setTitle(dto.title());
            proposal.setDescription(dto.description());
            proposal.setStart_date(dto.start_date());
            proposal.setExpiration_date(dto.expiration_date());
        }

        return errors;
    }

    public void deleteProposal(Long id, Long userId) throws Exception {
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new ProposalNotFoundException());

        User user = userRepository.findUserById(userId);

        if(!proposal.getUser().equals(user)) {
            throw new AccessDeniedException("User can't edit this proposal.");
        }

        if(voteRepository.existsByProposalId(id)) {
            throw new IllegalStateException("Can't delete this proposal because someone already voted.");
        }

        proposalRepository.delete(proposal);
    } 

    public VotesResponseDto getProposalResults(Long id, Long userId) throws Exception {
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new ProposalNotFoundException());
        
        User user = userRepository.findUserById(userId);

        if(!proposal.getUser().equals(user)) {
            throw new AccessDeniedException("User can't consult this proposal.");
        }

        String winner = "EMPATE";
        String status = "Not started";
        Float percentSim = (float) 0.0;
        Float percentNao = (float) 0.0;

        Integer totalVotes = (voteRepository.findAllByProposalId(id)).size();
        Integer totalSim = (voteRepository.findAllByProposalIdAndVote(id, VoteEnum.SIM)).size();
        Integer totalNao = (voteRepository.findAllByProposalIdAndVote(id, VoteEnum.NAO)).size();

        if(totalVotes > 0) {
            percentSim = ( (float) totalSim / totalVotes) * 100;
            percentNao = ( (float) totalNao / totalVotes) * 100;
        }

        if(percentSim == percentNao) winner = "EMPATE";
        else if(percentSim > percentNao) winner = "SIM";
        else if(percentNao > percentSim) winner = "NAO";

        if(proposal.getExpiration_date().isBefore(LocalDateTime.now())) status = "Finished";
        if(proposal.getExpiration_date().isAfter(LocalDateTime.now())) status = "Open";

        return new VotesResponseDto(proposal.getTitle(), percentSim, percentNao, winner, totalVotes, status);
    }
}
