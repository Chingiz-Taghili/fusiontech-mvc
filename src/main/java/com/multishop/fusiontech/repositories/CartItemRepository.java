package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(Long userId, Long productId);

    void deleteAllByUserId(Long userId);

    List<CartItem> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE user_id = ?1", nativeQuery = true)
    void deleteCartByUserId(Long user_id);
}
