package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "login-history")
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_History")
    private long idHistory;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "ip")
    private String ip;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "id_account", nullable = false)
    @JsonBackReference(value = "login_history-account")
    private Account account;
}
