package com.multishop.fusiontech.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRelatedDto {
    private Long id;
    private String name;
    private double price;
    private double discountPrice;
    private String image;
    private double rating;
}
