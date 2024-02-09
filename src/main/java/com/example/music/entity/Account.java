package com.example.music.entity;

import com.example.music.entity.comon.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
public class Account {

    @Id
    private String user_name;

    private String pass_word;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "account")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role roles;

    public Account() {
        this.date_create = new Date(new java.util.Date().getTime());
        this.status = Status.Activate;
    }

}
