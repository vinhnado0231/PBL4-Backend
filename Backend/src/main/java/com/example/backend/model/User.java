package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long idUser;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "address_user")
    private String addressUser;

    @Column(name = "phone_user")
    private String phoneUser;

    @Column(name = "gender_user")
    private boolean genderUser;

    @Column(name = "date_of_birth_user")
    private String dateOfBirthUser;

    @Column(name = "email_user")
    private String emailUser;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private Account account;

    @OneToMany(mappedBy = "user")
    @JsonBackReference()
    private Set<Friend> friends;

}
