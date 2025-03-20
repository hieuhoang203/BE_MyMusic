package com.example.music.song;

import com.example.music.album.Album;
import com.example.music.own.Own;
import com.example.music.song_favorite.SongFavorite;
import com.example.music.song_genres.SongGenres;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_song")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Song implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    private Album album;

    @OneToMany(mappedBy = "work",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Own> owns;

    @Column(name = "url")
    private String url;

    @Column(name = "duration")
    private Short duration;

    @Column(name = "view")
    private Integer view;

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    private Set<SongGenres> songGenres;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_date")
    private Date update_date;

    @Column(name = "update_by")
    private String update_by;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
    @JsonIgnore
    private Set<SongFavorite> songFavorites;

}
