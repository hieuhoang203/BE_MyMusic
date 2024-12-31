package com.example.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "song_genres")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SongGenres implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @ManyToOne
    @JoinColumn(name = "song")
    private Song song;

    @ManyToOne
    @JoinColumn(name = "genres")
    private Genres genres;

    @Column(name = "status")
    private String status;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_date")
    private Date update_date;

    @Column(name = "update_by")
    private String update_by;

}
