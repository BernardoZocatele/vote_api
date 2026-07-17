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
// adicionar logica de token (guardar o user id no token)
// configurar filtro no SecurityConfiguration
// configurar segurança das rotas no SecurityConfiguration
// remover user id dos request body e passar a utilizar o user id do token