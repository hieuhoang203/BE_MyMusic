package com.example.music.service;

import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.securityConfig.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountService service;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;



}
