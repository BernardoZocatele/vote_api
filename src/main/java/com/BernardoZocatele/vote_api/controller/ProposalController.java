package com.BernardoZocatele.vote_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;
import com.BernardoZocatele.vote_api.dto.request.EditProposalRequestDto;
import com.BernardoZocatele.vote_api.dto.response.SuccessResponseDto;
import com.BernardoZocatele.vote_api.dto.response.VotesResponseDto;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.infra.exception.GlobalRulesErrorMessage;
import com.BernardoZocatele.vote_api.service.ProposalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createProposal(@RequestBody @Valid CreateProposalRequestDto request) {
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userLogado.getId();

        List<String> errors = proposalService.checkProposal(request, userId);

        if(errors.isEmpty()) {
            proposalService.createProposal(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDto(HttpStatus.CREATED, "Proposal created."));
        }

        GlobalRulesErrorMessage threatResponse = new GlobalRulesErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProposal(@PathVariable Long id, @RequestBody EditProposalRequestDto request) throws Exception {
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userLogado.getId();

        List<String> errors = proposalService.editProposal(id, request, userId);

        if(errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDto(HttpStatus.OK, "Proposal edited."));
        }

        GlobalRulesErrorMessage threatResponse = new GlobalRulesErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProposal(@PathVariable Long id) throws Exception {
        User userLogado =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userLogado.getId();

        proposalService.deleteProposal(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDto(HttpStatus.OK, "Proposal deleted."));
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<?> resultProposal(@PathVariable long id) throws Exception {
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userLogado.getId();

        VotesResponseDto dto = proposalService.getProposalResults(id, userId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
