package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_user")
public class GroupUser {
    @EmbeddedId
    private GroupKey idGroupUser;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @MapsId("idGroup")
    @JoinColumn(name = "idGroup")
    private Group group;

    @Column(name = "id_read_message")
    private Long idReadMessage;
    //roleGroup = 1 : Truong nhom : Co quyen them, xoa, doi ten nhom
    //roleGroup = 2 : Pho nhom : Co quyen them, xoa
    //roleGroup = 0 : Thanh vien nhom
    @Column(name = "role_group")
    private int roleGroup;
}
