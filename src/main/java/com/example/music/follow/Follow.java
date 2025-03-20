package com.example.music.follow;

import com.example.music.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
    private java.util.Date create_date;

    @Column(name = "create_by", length = 40)
    private String create_by;

    @Column(name = "update_date")
    private Date update_date;

    @Column(name = "update_by", length = 40)
    private String update_by;

}