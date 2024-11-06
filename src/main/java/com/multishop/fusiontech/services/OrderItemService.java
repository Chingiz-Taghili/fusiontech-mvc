package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.orderItem.OrderItemCreateDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemUpdateDto;

public interface OrderItemService {
    OrderItemDto getOrderItemById(Long id);

    boolean createOrderItem(Long orderId, OrderItemCreateDto orderItemCreateDto);

    boolean updateOrderItem(Long id, OrderItemUpdateDto orderItemUpdateDto);

    void deleteOrderItem(Long id);
}
