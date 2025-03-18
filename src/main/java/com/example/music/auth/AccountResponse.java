package com.example.music.auth;

import lombok.Data;

@Data
public class AccountResponse {

    private String accessToken;

    private String login;

    public AccountResponse(String accessToken, String login) {
        this.accessToken = accessToken;
        this.login = login;
    }

}
