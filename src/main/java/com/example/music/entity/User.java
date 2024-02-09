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
@Table(name = "user")
@Setter
@Getter
@AllArgsConstructor
public class User implements Serializable {

    @Id
    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private String name;

    private Date birthday;

    private Boolean sex;

    private String nationality;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", referencedColumnName = "user_name")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image", referencedColumnName = "id")
    private Image image;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follow",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "idol")
    )
    @JsonIgnore
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    private Set<Album> albums;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<FavoritesList> favoritesLists;

    public User() {
        this.date_create = new Date(new java.util.Date().getTime());
        this.status = Status.Activate;
    }

}
