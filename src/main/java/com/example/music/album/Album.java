package com.example.music.album;

import com.example.music.song.Song;
import com.example.music.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "tbl_album")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Album implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artis")
    @JsonIgnore
    private User artis;

    @Column(name = "release_date")
    private Date release_date;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private Set<Song> songs;

}
