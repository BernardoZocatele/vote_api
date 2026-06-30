package com.BernardoZocatele.vote_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BernardoZocatele.vote_api.entity.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    
}
