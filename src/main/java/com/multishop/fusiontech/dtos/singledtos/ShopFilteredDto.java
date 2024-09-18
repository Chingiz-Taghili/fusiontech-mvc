package com.multishop.fusiontech.dtos.singledtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopFilteredDto {
    private int price;
    private int category;
    private int brand;
}
