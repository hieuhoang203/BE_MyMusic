package com.example.music.follow;

import com.example.music.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_follow")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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

    @Column(name = "status")
    private String status;

    @Column(name = "create_date")
    private Timestamp create_date;

    @Column(name = "create_by", length = 40)
    private String create_by;

    @Column(name = "update_date")
    private Timestamp update_date;

    @Column(name = "update_by", length = 40)
    private String update_by;

}