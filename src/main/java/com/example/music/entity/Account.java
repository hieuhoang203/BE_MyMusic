package com.example.music.entity;

import com.example.music.entity.comon.Role;
import com.example.music.entity.comon.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements Serializable {

    @Id
    private String login;

    @Column(length = 60)
    private String pass;

    private Date date_create;

    private Date date_update;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "account")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Role role;

}
