package com.example.music.entity;

import com.example.music.entity.comon.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category")
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    private String name;

    private Date date_create;

    private Date date_update;

    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Album> albums;

    public Category() {
        this.date_create = new Date(new java.util.Date().getTime());
        this.status = Status.Activate;
    }

}
