package com.multishop.fusiontech.dtos.singledtos;

import com.multishop.fusiontech.dtos.product.ProductBasketDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartDto {
    private List<ProductBasketDto> products;
    private double subtotal;
    private double shipping;
    private double total;
}
