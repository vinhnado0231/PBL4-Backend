package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_favorite")
public class UserFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_favorite")
    private long idUserFavorite;
    private Float an_uong=2.5f;
    private Float xem_phim=2.5f;
    private Float doc_sach=2.5f;
    private Float the_thao=2.5f;
    private Float ca_nhac=2.5f;
    private Float du_lich=2.5f;
    private Float coffee=2.5f;
    private Float choi_game=2.5f;
    private Float code=2.5f;
    private Float nau_an=2.5f;

    @OneToOne(mappedBy = "userFavorite")
        @JsonBackReference
    private User user;


}

