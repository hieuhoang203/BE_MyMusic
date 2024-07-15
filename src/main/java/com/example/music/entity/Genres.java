package com.example.music.entity;

import com.example.music.entity.comon.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "genres")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genres implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    private String code;

    private String name;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnore
    private Set<Song> songs;

}
