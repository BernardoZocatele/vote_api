package com.BernardoZocatele.vote_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BernardoZocatele.vote_api.dto.request.VoteRequestDto;
import com.BernardoZocatele.vote_api.infra.exception.GlobalRulesErrorMessage;
import com.BernardoZocatele.vote_api.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
    
    @PostMapping
    public ResponseEntity<?> vote(@RequestBody VoteRequestDto request) {
        List<String> errors = voteService.checkVote(request);

        if(errors.isEmpty()) {
            voteService.createVote(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Voted!");
        }

        GlobalRulesErrorMessage threatResponse = new GlobalRulesErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
