package com.example.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupKey implements Serializable {
    @Column(name = "id_user")
    private long idUser;

    @Column(name = "id_group")
    private long idGroup;
}
