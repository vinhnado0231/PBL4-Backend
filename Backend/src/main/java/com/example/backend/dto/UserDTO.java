package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long idUser;
    private String nameUser;
    private boolean genderUser;
    private String dateOfBirthUser;
    private String homeTownUser;
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

}
