package com.example.music.handle_exception;

import com.example.music.comon.Constant;
import com.example.music.comon.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<Object, Object> finalResult = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // Lấy danh sách lỗi và đưa vào response
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        finalResult.put(Constant.RESPONSE_KEY.DATA, errors);
        finalResult.put(Constant.RESPONSE_KEY.RESULT, HttpStatus.BAD_REQUEST.value());
        return ResponseData.createResponse(finalResult);
    }

}
