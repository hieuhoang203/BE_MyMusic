package com.example.music.controller.login.model.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NewAccountRequest {

    private String name;

    private String login;

    private String pass;

}
