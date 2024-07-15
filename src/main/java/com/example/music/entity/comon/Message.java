package com.example.music.entity.comon;

public enum Message {

    NOT_EXISTS("Account does not exist!"),
    PASSWORD_NOT_EXISTS("Incorrect password!"),
    EMAIL_USER_EXIST("User name already exists!");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
