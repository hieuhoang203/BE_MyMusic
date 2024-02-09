package com.example.music.entity;

import com.example.music.entity.comon.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "favoritesList")
@Getter
@Setter
@AllArgsConstructor
public class FavoritesList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favoritesAlbum",
            joinColumns = @JoinColumn(name = "id_favoritesList"),
            inverseJoinColumns = @JoinColumn(name = "id_Album")
    )
    @JsonIgnore
    private Set<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favoritesSong",
            joinColumns = @JoinColumn(name = "id_favoritesList"),
            inverseJoinColumns = @JoinColumn(name = "id_song")
    )
    @JsonIgnore
    private Set<Album> albums;

    public FavoritesList() {
        this.date_create = new Date(new java.util.Date().getTime());
        this.status = Status.Activate;
    }

}
