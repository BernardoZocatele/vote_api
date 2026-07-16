package com.BernardoZocatele.vote_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BernardoZocatele.vote_api.entity.VoteEnum;
import com.BernardoZocatele.vote_api.entity.Votes;

public interface VoteRepository extends JpaRepository<Votes, Long> {
    boolean existsByUserIdAndProposalId(Long userId, Long proposalId);

    boolean existsByProposalId(Long proposalId);

    List<Votes> findAllByProposalId(Long proposalId);

    List<Votes> findAllByProposalIdAndVote(Long proposalId, VoteEnum vote);
}
