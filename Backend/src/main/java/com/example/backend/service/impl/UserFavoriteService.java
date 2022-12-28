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
        userFavorite.setAn_uong(json.get("xem_phim"));
        userFavorite.setAn_uong(json.get("doc_sach"));
        userFavorite.setAn_uong(json.get("the_thao"));
        userFavorite.setAn_uong(json.get("ca_nhac"));
        userFavorite.setAn_uong(json.get("du_lich"));
        userFavorite.setAn_uong(json.get("coffee"));
        userFavorite.setAn_uong(json.get("choi_game"));
        userFavorite.setAn_uong(json.get("code"));
        userFavorite.setAn_uong(json.get("nau_an"));
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
