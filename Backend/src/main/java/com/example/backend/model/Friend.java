package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "id_friend")
    private long idFriend;

    @Column(name = "is_request")
    private boolean isrequest;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
