package com.example.music.user;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

public interface UserDAO {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.gender}")
    Boolean getGender();

    @Value("#{target.birthday}")
    Date getBirthDay();

    @Value("#{target.dateCreate}")
    Date getDateCreate();

    @Value("#{target.status}")
    String getStatus();

    @Value("#{target.role}")
    String getRole();

}
