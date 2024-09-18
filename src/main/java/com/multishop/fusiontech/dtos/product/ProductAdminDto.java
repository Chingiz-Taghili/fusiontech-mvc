package com.multishop.fusiontech.dtos.product;

import com.multishop.fusiontech.dtos.category.CategoryHomeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAdminDto {
    private Long id;
    private String name;
    private double price;
    private String image;
    private CategoryHomeDto category;
}
