package com.BernardoZocatele.vote_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BernardoZocatele.vote_api.entity.Votes;

public interface VoteRepository extends JpaRepository<Votes, Long> {
    boolean existsByUserIdAndProposalId(Long userId, Long proposalId);
}
