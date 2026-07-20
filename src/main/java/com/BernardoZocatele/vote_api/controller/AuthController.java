package com.BernardoZocatele.vote_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BernardoZocatele.vote_api.dto.request.LoginRequestDto;
import com.BernardoZocatele.vote_api.dto.request.RegisterRequestDto;
import com.BernardoZocatele.vote_api.dto.response.LoginResponseDto;
import com.BernardoZocatele.vote_api.dto.response.RegisterResponseDto;
import com.BernardoZocatele.vote_api.entity.User;
import com.BernardoZocatele.vote_api.infra.exception.RegisterUserException;
import com.BernardoZocatele.vote_api.infra.security.TokenService;
import com.BernardoZocatele.vote_api.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.cpf(), request.password());
        Authentication auth = authenticationManager.authenticate(userAndPass);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request) {
        User newUser = new User();

        if(userRepository.existsByCpf(request.cpf())) {
            throw new RegisterUserException();
        }

        newUser.setName(request.name());
        newUser.setCpf(request.cpf());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDto("New user registered."));
    }
}
