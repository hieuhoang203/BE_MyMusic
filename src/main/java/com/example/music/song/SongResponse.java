package com.example.music.song;

import com.example.music.album.Album;
import com.example.music.genres.GenresResponse;
import com.example.music.user.ArtisResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {

    private String id;

    private String name;

    private String avatar;

    private String url;

    private Integer view;

    private Short duration;

    private Album album;

    private List<GenresResponse> genres;

    private List<ArtisResponse> artists;

    private String status;

}
