package com.BernardoZocatele.vote_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteApiApplication.class, args);
	}

}

// FALTA FAZER 
// Arrumar as exceções que são lamçadas na camada de filtro, que ignoram o global exception handler
