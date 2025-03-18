package com.example.music.song;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class SongRequest {

    private String name;

    private MultipartFile avatar;

    private MultipartFile sound;

    private String album;

    private List<String> artis;

    private List<String> genres;

    private Short duration;

}
