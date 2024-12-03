package com.example.music.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable{

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private Boolean gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", referencedColumnName = "login")
    @JsonIgnore
    private Account account;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private String status;

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
