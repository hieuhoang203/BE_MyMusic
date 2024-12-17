package com.example.music.service;

import com.example.music.controller.login.model.request.NewAccountRequest;
import com.example.music.entity.Account;
import com.example.music.entity.User;
import com.example.music.entity.comon.Constant;
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
                .role(Constant.Role.USER)
                .create_date(new Date(new java.util.Date().getTime()))
                .status(Constant.Status.Activate)
                .build();
        this.accountRepository.save(account);

        User user = User.builder()
                .name(newAccountRequest.getName())
                .create_date(new Date(new java.util.Date().getTime()))
                .account(account)
                .status(Constant.Status.Activate)
                .build();
        this.userRepository.save(user);
    }

    public Account createAccountArtis(NewAccountRequest newAccountRequest) {
        Account account = Account.builder()
                .login(newAccountRequest.getLogin())
                .pass(passwordEncoder.encode(newAccountRequest.getPass()))
                .role(Constant.Role.ARTIS)
                .create_date(new Date(new java.util.Date().getTime()))
                .status(Constant.Status.Activate)
                .build();
        this.accountRepository.save(account);

        User user = User.builder()
                .name(newAccountRequest.getName())
                .create_date(new Date(new java.util.Date().getTime()))
                .account(account)
                .status(Constant.Status.Activate)
                .build();
        this.userRepository.save(user);
        return account;
    }

}
