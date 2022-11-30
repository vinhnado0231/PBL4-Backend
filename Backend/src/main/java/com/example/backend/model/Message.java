package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private long idMessage;

    @Column(name = "id_sender")
    private long idSender;

    @Column(name = "is_text")
    private boolean isText;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "message")
    private String message;

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "id_group", nullable = false)
    @JsonBackReference(value = "message_group")
    private Group group;

    @OneToMany(mappedBy = "message")
    private List<MessageReact> messageReactList;
}
