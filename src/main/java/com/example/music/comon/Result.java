package com.example.music.comon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Result {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("status")
    private Boolean status;

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
