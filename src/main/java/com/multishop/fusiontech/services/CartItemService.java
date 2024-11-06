package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.cart.CartItemCreateDto;

public interface CartItemService {

    boolean addToCart(CartItemCreateDto cartItemCreateDto, String userEmail);

    void deleteCartItem(Long productId, String userEmail);
}
