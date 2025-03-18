package com.example.music.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String id;

    private String name;

    private String birthday;

    private Boolean gender;

    private MultipartFile avatar;

    private String email;

    private String password;

    private String role;

}
