package com.example.backend.service;

import com.example.backend.model.UserFavorite;

import java.util.Map;

public interface IUserFavoriteService {
    void UpdateUserFavorite(long idUSer, Map<String, Float> json);

    UserFavorite findUserFavoriteByIdUser(long idUser);

    void SaveUserFavorite(UserFavorite userFavorite);
}
