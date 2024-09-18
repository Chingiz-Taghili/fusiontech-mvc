package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
