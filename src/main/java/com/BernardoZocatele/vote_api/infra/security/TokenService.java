package com.BernardoZocatele.vote_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.BernardoZocatele.vote_api.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String jwt_secret;
    
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

            var token = JWT.create()
                .withIssuer("vote-api")
                .withSubject(user.getCpf())
                .withClaim("userId", user.getId())
                .withExpiresAt(generateExpirationDate());

            return token.sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Login error");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

            return JWT.require(algorithm)
                .withIssuer("vote-api")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}