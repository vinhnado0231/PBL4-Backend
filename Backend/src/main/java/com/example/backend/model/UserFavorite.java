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
    private Float an_uong;
    private Float xem_phim;
    private Float doc_sach;
    private Float the_thao;
    private Float ca_nhac;
    private Float du_lich;
    private Float coffee;
    private Float choi_game;
    private Float code;
    private Float nau_an;

    @OneToOne(mappedBy = "userFavorite")
        @JsonBackReference
    private User user;

    public UserFavorite(long idUserFavorite, Float an_uong, Float xem_phim, Float doc_sach, Float the_thao, Float ca_nhac, Float du_lich, Float coffee, Float choi_game, Float code, Float nau_an, User user) {
        this.idUserFavorite = idUserFavorite;
        this.an_uong = an_uong;
        this.xem_phim = xem_phim;
        this.doc_sach = doc_sach;
        this.the_thao = the_thao;
        this.ca_nhac = ca_nhac;
        this.du_lich = du_lich;
        this.coffee = coffee;
        this.choi_game = choi_game;
        this.code = code;
        this.nau_an = nau_an;
        this.user = user;
    }
}

