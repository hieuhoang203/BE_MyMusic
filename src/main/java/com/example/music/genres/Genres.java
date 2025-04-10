package com.example.music.genres;

import com.example.music.song_genres.SongGenres;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
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
    private Timestamp create_date;

    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_date")
    private Timestamp update_date;

    @Column(name = "update_by")
    private String update_by;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnore
    private Set<SongGenres> songGenres;

}
