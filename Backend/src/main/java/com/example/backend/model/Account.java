package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private long idAccount;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private User user;
}
