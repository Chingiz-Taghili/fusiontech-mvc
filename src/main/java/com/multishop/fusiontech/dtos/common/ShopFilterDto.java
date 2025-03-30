package com.multishop.fusiontech.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopFilterDto {
    private int price;
    private int category;
    private int brand;
}
