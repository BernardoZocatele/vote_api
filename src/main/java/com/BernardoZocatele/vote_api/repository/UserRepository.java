package com.BernardoZocatele.vote_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.BernardoZocatele.vote_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByCpf(String cpf);
    
}
