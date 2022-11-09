package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "message_react")
public class MessageReact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message_react")
    private long idMessageReact;

    @Column(name = "react")
    private long react;

    @Column(name = "id_sender")
    private long id_sender;

    @ManyToOne(targetEntity = Message.class)
    @JoinColumn(name = "id_message", nullable = false)
    @JsonBackReference
    private Message message;
}
