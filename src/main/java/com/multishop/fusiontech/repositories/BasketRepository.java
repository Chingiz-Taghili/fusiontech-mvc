package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Basket findByUserIdAndProductId(Long userId, Long productId);
}
