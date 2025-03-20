package com.example.music.favorite;

import com.example.music.song_favorite.SongFavorite;
import com.example.music.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_favorite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorite implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

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

    @OneToMany(mappedBy = "favorite", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<SongFavorite> favorites;

}
