package com.BernardoZocatele.vote_api.dto.response;


public record VotesResponseDto(Float percentSim, Float percentNao, String winner, Integer totalVotes, String status) {
}
