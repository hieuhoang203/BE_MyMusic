package com.example.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "follow")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Follow implements Serializable {

    @Id
    @Column(name = "id", length = 40)
    private String id;

    @ManyToOne
    @JoinColumn(name = "idol")
    private User idol;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private String status;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "create_by", length = 40)
    private String create_by;

    @Column(name = "update_date")
    private Date update_date;

    @Column(name = "update_by", length = 40)
    private String update_by;

}
