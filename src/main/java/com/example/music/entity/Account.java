package com.example.music.entity;

import com.example.music.entity.comon.Constant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account implements Serializable {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "pass")
    private String pass;

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

    @OneToOne(mappedBy = "account")
    private User user;

    @Column(name = "role")
    private String role;

}
