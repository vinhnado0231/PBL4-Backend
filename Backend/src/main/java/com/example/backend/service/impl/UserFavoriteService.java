package com.example.backend.service.impl;

import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IUserFavoriteRepository;
import com.example.backend.service.IUserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserFavoriteService implements IUserFavoriteService {
    @Autowired
    private IUserFavoriteRepository userFavoriteRepository;

    @Override
    public void UpdateUserFavorite(long idUSer, Map<String, Float> json) {
        UserFavorite userFavorite = findUserFavoriteByIdUser(idUSer);
        userFavorite.setAn_uong(json.get("an_uong"));
        userFavorite.setXem_phim(json.get("xem_phim"));
        userFavorite.setDoc_sach(json.get("doc_sach"));
        userFavorite.setThe_thao(json.get("the_thao"));
        userFavorite.setCa_nhac(json.get("ca_nhac"));
        userFavorite.setDu_lich(json.get("du_lich"));
        userFavorite.setCoffee(json.get("coffee"));
        userFavorite.setChoi_game(json.get("choi_game"));
        userFavorite.setCode(json.get("code"));
        userFavorite.setNau_an(json.get("nau_an"));
        SaveUserFavorite(userFavorite);
    }

    @Override
    public UserFavorite findUserFavoriteByIdUser(long idUser) {
        return userFavoriteRepository.findByIdUser(idUser);
    }

    @Override
    public void SaveUserFavorite(UserFavorite userFavorite) {
         userFavoriteRepository.save(userFavorite);;
    }
}
