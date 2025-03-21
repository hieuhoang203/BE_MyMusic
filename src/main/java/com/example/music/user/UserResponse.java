package com.example.music.user;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

public interface UserResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.gender}")
    Boolean getGender();

    @Value("#{target.birthday}")
    Date getBirthday();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.status}")
    String getStatus();

}
