package com.example.music.service;

import com.example.music.controller.login.model.request.LoginRequest;
import com.example.music.controller.login.model.request.NewAccountRequest;
import com.example.music.controller.login.model.response.AccountResponse;
import com.example.music.controller.login.model.response.UserResponse;
import com.example.music.entity.Account;
import com.example.music.entity.User;
import com.example.music.entity.comon.Constant;
import com.example.music.entity.comon.Message;
import com.example.music.entity.comon.Result;
import com.example.music.repositories.AccountRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.securityConfig.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> createAccountUser(NewAccountRequest newAccountRequest) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Account account = Account.builder()
                    .login(newAccountRequest.getLogin())
                    .pass(passwordEncoder.encode(newAccountRequest.getPass()))
                    .role(Constant.Role.USER)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .status(Constant.Status.Activate)
                    .build();
            this.accountRepository.save(account);

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(newAccountRequest.getName())
                    .create_date(new Date(new java.util.Date().getTime()))
                    .account(account)
                    .status(Constant.Status.Activate)
                    .build();
            this.userRepository.save(user);
            finalResult.put(Constant.RESPONSE_KEY.DATA, jwtConfig.generateToken(newAccountRequest.getLogin()));
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi tạo mới người dùng {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_CREATE_ACCOUNT.getCode(), false, Message.UNABLE_TO_CREATE_ACCOUNT.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Map<Object, Object> createAccountArtis(NewAccountRequest newAccountRequest) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Account account = Account.builder()
                    .login(newAccountRequest.getLogin())
                    .pass(passwordEncoder.encode(newAccountRequest.getPass()))
                    .role(Constant.Role.ARTIS)
                    .create_date(new Date(new java.util.Date().getTime()))
                    .status(Constant.Status.Activate)
                    .build();
            this.accountRepository.save(account);

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(newAccountRequest.getName())
                    .create_date(new Date(new java.util.Date().getTime()))
                    .account(account)
                    .status(Constant.Status.Activate)
                    .build();
            this.userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi tạo mới tài khoản nghệ sĩ {} " + e.getMessage());
            result = new Result(Message.UNABLE_TO_CREATE_ACCOUNT.getCode(), false, Message.UNABLE_TO_CREATE_ACCOUNT.getMessage());
            throw e;
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> login(LoginRequest login) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPass()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtConfig.generateToken(authentication.getName());
            AccountResponse response = new AccountResponse(token, login.getLogin());
            finalResult.put(Constant.RESPONSE_KEY.DATA, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = new Result(Message.CANNOT_LOG_IN.getCode(), false, Message.CANNOT_LOG_IN.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

    public Map<Object, Object> getUserResponse(String login) {
        Map<Object, Object> finalResult = new HashMap<>();
        Result result = Result.OK();
        try {
            UserResponse userResponse = userRepository.getUserResponse(login);
            finalResult.put(Constant.RESPONSE_KEY.DATA, userResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = new Result(Message.NOT_EXISTS.getCode(), false, Message.NOT_EXISTS.getMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.RESULT, result);
        return finalResult;
    }

}
