package com.example.music.genres;


import org.springframework.beans.factory.annotation.Value;

public interface GenresResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

}
