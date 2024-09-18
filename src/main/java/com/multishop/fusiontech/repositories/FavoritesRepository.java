package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorite, Long> {

    Favorite findByUserIdAndProductId(Long userId, Long productId);
}
