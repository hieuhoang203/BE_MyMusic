package com.example.music.user;

import lombok.*;
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
