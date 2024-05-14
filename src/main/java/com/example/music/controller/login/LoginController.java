package com.example.music.controller.login;

import com.example.music.controller.login.model.request.LoginRequest;
import com.example.music.controller.login.model.request.NewAccountRequest;
import com.example.music.controller.login.model.response.AccountResponse;
import com.example.music.controller.login.model.response.UserResponse;
import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.securityConfig.JwtConfig;
import com.example.music.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin("*")
public class LoginController {

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

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody NewAccountRequest accountRequest) {
        if (this.repository.existsAccountByLogin(accountRequest.getLogin())) {
            return new ResponseEntity<>("User name taken!", HttpStatus.BAD_REQUEST);
        }
        this.service.createAccountUser(accountRequest);
        return new ResponseEntity<>(jwtConfig.generateToken(accountRequest.getLogin()), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AccountResponse> login(@RequestBody LoginRequest login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtConfig.generateToken(authentication.getName());
            return new ResponseEntity<>(new AccountResponse(token, login.getLogin()), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-account-by-user-name")
    public ResponseEntity<UserResponse> getUserResponse(@RequestParam(name = "login") String login) {
        return new ResponseEntity<>(userRepository.getUserResponse(login), HttpStatus.OK);
    }

}
