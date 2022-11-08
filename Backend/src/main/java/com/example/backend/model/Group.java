package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private long idGroup;

    @Column(name = "is_single")
    private boolean isSingle;

//    @ManyToMany
//    @JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_user"))
//    private Set<User> users;

    @OneToMany(mappedBy = "group")
    @JsonBackReference(value = "group_message")
    private List<Message> messageList;
}

