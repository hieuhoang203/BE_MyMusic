package com.example.music.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnore
    private Set<SongGenres> songGenres;

}
