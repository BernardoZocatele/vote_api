package com.BernardoZocatele.vote_api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BernardoZocatele.vote_api.entity.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    @Query("SELECT p. expiration_date FROM Proposal p WHERE p.id = :id")
    LocalDateTime findExpirationDateById(@Param("id") Long id);

    Proposal findProposalById(Long id);
    
}
