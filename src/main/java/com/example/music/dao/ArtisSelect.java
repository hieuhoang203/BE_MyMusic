package com.example.music.dao;

import org.springframework.beans.factory.annotation.Value;

public interface ArtisSelect {

    @Value("#{target.value}")
    Byte getValue();

    @Value("#{target.label}")
    String getLabel();

}
