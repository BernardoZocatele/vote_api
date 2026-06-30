package com.BernardoZocatele.vote_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BernardoZocatele.vote_api.dto.request.CreateProposalRequestDto;
import com.BernardoZocatele.vote_api.entity.Proposal;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(prop.getTitle() + " created!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
