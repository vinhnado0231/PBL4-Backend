package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    @Column(name = "token")
    private String token;

    @Column(name = "status")
    private boolean status;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "account")
    private List<LoginHistory> loginHistoryList;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
