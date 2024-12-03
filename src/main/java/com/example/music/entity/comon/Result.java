package com.example.music.entity.comon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("responseMessage")
    private String message;

    public static Result OK() {
        return new Result("200", true, "Successful!");
    }

    public static Result formError(String code, String message) {
        return new Result(code, false, message);
    }

    public static Result errSystem() {
        return new Result("500", false, "Error system!");
    }

}
