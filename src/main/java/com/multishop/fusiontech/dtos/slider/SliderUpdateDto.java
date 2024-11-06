package com.multishop.fusiontech.dtos.slider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderUpdateDto {
    private String title;
    private String description;
    private String image;
    private String url;
    private boolean active;
}
