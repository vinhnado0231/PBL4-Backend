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
    private String addressEmployee;

    @Column(name = "phone_user")
    private String phoneEmployee;

    @Column(name = "gender_user")
    private boolean genderEmployee;

    @Column(name = "date_of_birth_user")
    private String dateOfBirthEmployee;

    @Column(name = "email_user")
    private String emailEmployee;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    //Them account cho user
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private Account account;

    //Them group cho user
//    @ManyToMany(mappedBy = "users")
//    private Set<Group> groups;

    @OneToMany(mappedBy = "user")
    @JsonBackReference()
    private Set<Friend> friends;

}
