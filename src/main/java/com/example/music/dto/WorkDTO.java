package com.example.music.dto;

import com.example.music.entity.Genres;
import com.example.music.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class WorkDTO {

    private String name;

    private MultipartFile avatar;

    private MultipartFile sound;

    private Long album;

    private Set<User> artis;

    private Set<Genres> genres;

    private Short duration;

}
