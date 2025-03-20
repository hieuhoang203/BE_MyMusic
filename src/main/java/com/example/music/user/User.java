package com.example.music.user;

import com.example.music.album.Album;
import com.example.music.favorite.Favorite;
import com.example.music.follow.Follow;
import com.example.music.own.Own;
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
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable{

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

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

    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Follow> user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idol")
    @JsonIgnore
    private Set<Follow> idol;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artis")
    @JsonIgnore
    private Set<Album> albums;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Favorite> favorite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    private Set<Own> owns;

}
