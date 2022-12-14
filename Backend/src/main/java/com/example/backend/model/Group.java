package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private long idGroup;

    @Column(name = "is_single")
    private boolean isSingle;

    @Column(name = "name_group")
    private String nameGroup;

    @Lob
    @Column(name = "avatar_group")
    private String avatarGroup;

    @OneToMany(mappedBy = "group")
    private List<Message> messageList;
}

