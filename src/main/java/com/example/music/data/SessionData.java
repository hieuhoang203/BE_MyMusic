package com.example.music.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionData {

    private String session_id;

    private String user_id;

    private String user_name;

    private String display_name;

}
