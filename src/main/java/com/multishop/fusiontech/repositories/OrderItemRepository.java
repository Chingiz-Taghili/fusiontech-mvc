package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
