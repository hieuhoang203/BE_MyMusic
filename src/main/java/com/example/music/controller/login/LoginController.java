package com.example.music.controller.login;

import com.example.music.dto.AccountRequest;
import com.example.music.dto.LoginRequest;

import com.example.music.entity.comon.ResponseData;
import com.example.music.repositories.UserRepository;
import com.example.music.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginController {

    private final AccountService service;

    private final UserRepository userRepository;

    @PostMapping(value = "/register")
    public CompletableFuture<ResponseData> register(@RequestBody AccountRequest accountRequest) throws Exception {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.service.createAccountUser(accountRequest)));
    }

    @PostMapping(value = "/login")
    public CompletableFuture<ResponseData> login(@RequestBody LoginRequest login) {
        if (login.getLogin().isEmpty() || login.getPass().isEmpty()) {
            throw new IllegalArgumentException("Login or password cannot be null");
        }
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.service.login(login)));
    }

    @GetMapping(value = "/get-account-by-user-name")
    public CompletableFuture<ResponseData> getUserResponse(@RequestParam(name = "login") String login) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(this.service.getUserResponse(login)));
    }

}
