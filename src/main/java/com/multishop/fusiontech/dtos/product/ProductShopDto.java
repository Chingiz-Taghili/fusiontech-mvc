package com.multishop.fusiontech.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductShopDto {
    private Long id;
    private String name;
    private double price;
    private String image;
    private double discountPrice;
    private double rating;
}
