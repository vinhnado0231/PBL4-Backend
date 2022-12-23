package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_user")
public class GroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGroupUser;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(name = "id_read_message")
    private Long idReadMessage;
    //roleGroup = 1 : Truong nhom : Co quyen them, xoa, doi ten nhom
    //roleGroup = 2 : Pho nhom : Co quyen them, xoa
    //roleGroup = 0 : Thanh vien nhom
    @Column(name = "role_group")
    private int roleGroup;
}
