package com.example.music.dto;

import com.example.music.entity.Own;
import com.example.music.entity.SongGenres;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@Builder
public class WorkDTO {

    private String name;

    private MultipartFile avatar;

    private MultipartFile sound;

    private String album;

    private Set<Own> artis;

    private Set<SongGenres> genres;

    private Short duration;

}
