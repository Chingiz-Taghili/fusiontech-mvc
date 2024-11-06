package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.message) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.surname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.billAddress) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.billCity) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.billMessage) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.billName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.billSurname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Order> findByKeywordInColumnsIgnoreCase(@Param("keyword") String keyword);
}
