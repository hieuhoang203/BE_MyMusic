package com.example.music.controller.login.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

public interface UserResponse {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.gender}")
    Boolean getGender();

    @Value("#{target.birthday}")
    Date getBirthday();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.role}")
    String getRole();

}
