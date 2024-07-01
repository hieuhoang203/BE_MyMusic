package com.example.music.entity;

import com.example.music.entity.comon.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "song")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    private Album album;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "own",
            joinColumns = @JoinColumn(name = "work"),
            inverseJoinColumns = @JoinColumn(name = "author")
    )
    private Set<User> artis;

    private String url;

    private Short duration;

    private Integer view;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "songGenres",
            joinColumns = @JoinColumn(name = "id_song"),
            inverseJoinColumns = @JoinColumn(name = "id_genres")
    )
    private Set<Genres> genres;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @JsonIgnore
    private Set<Favorite> favorite; 

}
