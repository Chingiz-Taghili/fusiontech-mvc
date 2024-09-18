package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.order.OrderPlaceDto;

public interface OrderService {

    boolean checkout(String userEmail, OrderPlaceDto placeOrderDto);

}
