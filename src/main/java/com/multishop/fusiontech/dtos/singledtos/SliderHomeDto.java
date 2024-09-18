package com.multishop.fusiontech.dtos.singledtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderHomeDto {
    private Long id;
    private String title;
    private String description;
    private String image;
}
