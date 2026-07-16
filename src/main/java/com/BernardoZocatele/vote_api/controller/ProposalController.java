package com.BernardoZocatele.vote_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.BernardoZocatele.vote_api.dto.response.VotesResponseDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
import com.BernardoZocatele.vote_api.infra.exception.GlobalRulesErrorMessage;
import com.BernardoZocatele.vote_api.service.ProposalService;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createProposal(@RequestBody CreateProposalRequestDto request) {

        List<String> errors = proposalService.checkProposal(request);

        if(errors.isEmpty()) {
            Proposal prop = proposalService.createProposal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("'" + prop.getTitle() + "'" + " created.");
        }

        GlobalRulesErrorMessage threatResponse = new GlobalRulesErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProposal(@PathVariable Long id, @RequestBody EditProposalRequestDto request) throws Exception {
        List<String> errors = proposalService.editProposal(id, request);

        if(errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Proposal edited.");
        }

        GlobalRulesErrorMessage threatResponse = new GlobalRulesErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProposal(@PathVariable Long id, @RequestBody Long userId) throws Exception {
        proposalService.deleteProposal(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body("Proposal deleted!");
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<?> resultProposal(@PathVariable long id, @RequestBody Long userId) throws Exception {
        VotesResponseDto dto = proposalService.getProposalResults(id, userId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
