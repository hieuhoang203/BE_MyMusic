package com.example.music.entity.comon;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class RestAPIRuntime extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    public RestAPIRuntime(Message statusCode) {
        this.message = statusCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
