package com.example.music.album;


import org.springframework.beans.factory.annotation.Value;

public interface AlbumResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.artis}")
    String getArtis();

    @Value("#{target.release_date}")
    String getReleaseDate();

}
