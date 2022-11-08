package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private long idMessage;

    @Column(name="id_sender")
    private long idSender;

    @Column(name = "is_text")
    private boolean isText;

    @Column(name = "message")
    private String message;

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "id_group", nullable = false)
    @JsonBackReference
    private Group group;
}
