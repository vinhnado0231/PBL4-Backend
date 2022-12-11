package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
    private Float hoi_hoa;
    private Float hoc;
    private Float ngu;
    private Float mua_sam;
    private Float nuoi_thu;
    private Float trang_diem;
    private Float nau_an;

    @OneToOne(mappedBy = "userFavorite")
    @JsonBackReference
    private User user;
}

