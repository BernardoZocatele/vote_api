package com.BernardoZocatele.vote_api.infra.exception;

public class RegisterUserException extends RuntimeException {
    public RegisterUserException() { super("Error on register user."); }

    public RegisterUserException(String message) { super(message); }
}
