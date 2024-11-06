package com.multishop.fusiontech.dtos.orderItem;

import com.multishop.fusiontech.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateDto {
    private Product product;
    private double price;
    private int quantity;
}
