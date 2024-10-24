package com.multishop.fusiontech.dtos.product;

import com.multishop.fusiontech.models.Brand;
import com.multishop.fusiontech.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAdminDto {
    private Long id;
    private String name;
    private double price;
    private double discountPrice;
    private LocalDateTime discountDate;
    private Brand brand;
    private Category category;
}
