package com.example.music.user;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface ArtisResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.gender}")
    Boolean getGender();

    @Value("#{target.birthday}")
    Date getBirthday();

    @Value("#{target.songs}")
    Integer getSongs();

    @Value("#{target.follows}")
    Integer getFollows();

    @Value("#{target.status}")
    String getStatus();

}
