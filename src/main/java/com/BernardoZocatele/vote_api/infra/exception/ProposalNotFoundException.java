package com.BernardoZocatele.vote_api.infra.exception;

public class ProposalNotFoundException extends RuntimeException {

    public ProposalNotFoundException() { super("Proposal not found."); }

    public ProposalNotFoundException(String message) { super(message); }
    
}
