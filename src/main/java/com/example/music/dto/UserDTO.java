package com.example.music.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserDTO {

    private String id;

    private String name;

    private String birthday;

    private String gender;

    private MultipartFile avatar;

    private String email;

    private String role;

}
