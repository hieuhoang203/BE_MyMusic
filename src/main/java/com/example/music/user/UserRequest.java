package com.example.music.user;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name can not null")
    private String name;

    @NotBlank(message = "Birthday can not null")
    private String birthday;

    @NotBlank(message = "Gender can not null")
    private Boolean gender;

    private MultipartFile avatar;

    @NotBlank(message = "Email can not null")
    private String email;

    private String password;

    private String role;

}
