package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(Long userId, Long productId);
}
