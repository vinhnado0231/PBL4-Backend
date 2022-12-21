package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private long idMessage;

    @Column(name = "id_sender")
    private long idSender;

    @Column(name = "type")
    private int type;

    @Column(name = "time")
    private LocalDateTime time;

    @Lob
    @Column(name = "message",length = 100000000)
    private String message;

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "id_group", nullable = false)
    @JsonBackReference(value = "message_group")
    private Group group;

    @OneToMany(mappedBy = "message")
    private List<MessageReact> messageReactList;

    public Message(LocalDateTime time, String message) {
        this.time = time;
        this.message = message;
    }
}
