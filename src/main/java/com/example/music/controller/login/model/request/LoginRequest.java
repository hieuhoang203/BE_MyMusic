package com.example.music.controller.login.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {

    @NonNull
    private String login;

    @NonNull
    private String pass;

}
