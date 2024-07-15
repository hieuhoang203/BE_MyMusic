package com.example.music.service.impl;

import com.example.music.controller.login.model.request.NewAccountRequest;
import com.example.music.entity.Account;
import com.example.music.entity.User;
import com.example.music.entity.comon.Role;
import com.example.music.entity.comon.Status;
import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccountUser(NewAccountRequest newAccountRequest) {
        Account account = Account.builder()
                .login(newAccountRequest.getLogin())
                .pass(passwordEncoder.encode(newAccountRequest.getPass()))
                .role(Role.USER)
                .date_create(new Date(new java.util.Date().getTime()))
                .status(Status.Activate)
                .build();
        this.accountRepository.save(account);

        User user = User.builder()
                .name(newAccountRequest.getName())
                .date_create(new Date(new java.util.Date().getTime()))
                .account(account)
                .status(Status.Activate)
                .build();
        this.userRepository.save(user);
    }

    public Account createAccountArtis(NewAccountRequest newAccountRequest) {
        Account account = Account.builder()
                .login(newAccountRequest.getLogin())
                .pass(passwordEncoder.encode(newAccountRequest.getPass()))
                .role(Role.ARTIS)
                .date_create(new Date(new java.util.Date().getTime()))
                .status(Status.Activate)
                .build();
        this.accountRepository.save(account);

        User user = User.builder()
                .name(newAccountRequest.getName())
                .date_create(new Date(new java.util.Date().getTime()))
                .account(account)
                .status(Status.Activate)
                .build();
        this.userRepository.save(user);
        return account;
    }

}
