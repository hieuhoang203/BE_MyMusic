package com.example.music.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@Builder
public class SongDTO {

    private String name;

    private MultipartFile avatar;

    private MultipartFile sound;

    private Long album;

    private List<Integer> artis;

    private List<Byte> genres;

    private Short duration;

}
