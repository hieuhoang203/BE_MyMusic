package com.example.music.genres;

import com.example.music.song_genres.SongGenres;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_genres")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genres implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "name", length = 40)
    private String name;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnore
    private Set<SongGenres> songGenres;

}
