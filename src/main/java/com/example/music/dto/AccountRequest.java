package com.example.music.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRequest {

    @NonNull
    private String name;

    @NonNull
    private String login;

    @NonNull
    private String pass;

}
