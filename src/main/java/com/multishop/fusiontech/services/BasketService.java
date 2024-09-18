package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.singledtos.BasketAddDto;

public interface BasketService {

    boolean addToCart(BasketAddDto basketAddDto, String userEmail);

    boolean removeBasket(Long productId, String userEmail);
}
