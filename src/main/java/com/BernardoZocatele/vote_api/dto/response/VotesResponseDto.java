package com.BernardoZocatele.vote_api.dto.response;


public record VotesResponseDto(String name, Float percentSim, Float percentNao, String winner, Integer totalVotes, String status) {
}
