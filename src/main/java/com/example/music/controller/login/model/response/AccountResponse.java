package com.example.music.controller.login.model.response;

import com.example.music.entity.comon.Status;
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
