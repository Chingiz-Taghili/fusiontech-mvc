package com.multishop.fusiontech.services;

public interface FavoritesService {

    boolean addToFavorites(Long productId, String userEmail);

    boolean deleteFavorite(Long productId, String userEmail);
}
