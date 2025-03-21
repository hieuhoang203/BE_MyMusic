package com.example.music.album;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class AlbumRequest {

    private String name;

    private MultipartFile avatar;

    private String artis;

    private String release_date;

    private List<Integer> songs;

}
