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
    private Float an_uong = 2.5f;
    private Float xem_phim = 2.5f;
    private Float doc_sach = 2.5f;
    private Float the_thao = 2.5f;
    private Float ca_nhac = 2.5f;
    private Float du_lich = 2.5f;
    private Float coffee = 2.5f;
    private Float choi_game = 2.5f;
    private Float code = 2.5f;
    private Float nau_an = 2.5f;

}
