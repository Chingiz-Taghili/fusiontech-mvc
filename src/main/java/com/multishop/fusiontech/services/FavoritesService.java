package com.multishop.fusiontech.services;

import com.multishop.fusiontech.models.Favorite;

public interface FavoritesService {

    boolean addToFavorites(Long productId, String userEmail);

    boolean removeFromFavorites(Long productId, String userEmail);
}
