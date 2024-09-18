package com.multishop.fusiontech.dtos.singledtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketAddDto {
    private Long productId;
    private Long quantity;
}
