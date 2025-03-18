package com.example.music.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailAccount {

    private String id;

    private String name;

    private Boolean gender;

    private Date birthday;

    private String avatar;

    private String role;

}
