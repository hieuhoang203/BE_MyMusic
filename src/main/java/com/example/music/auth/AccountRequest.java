package com.example.music.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRequest {

    @NotBlank(message = "Name can not blank")
    private String name;

    @NotBlank(message = "User name can not blank")
    private String login;

    @NotBlank(message = "Password can not blank")
    @Size(min = 6, max = 20, message = "Password is valid")
    private String pass;

}
