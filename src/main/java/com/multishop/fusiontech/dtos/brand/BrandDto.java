package com.multishop.fusiontech.dtos.brand;

import com.multishop.fusiontech.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    private Long id;
    private String name;
    private List<Product> products;
}
