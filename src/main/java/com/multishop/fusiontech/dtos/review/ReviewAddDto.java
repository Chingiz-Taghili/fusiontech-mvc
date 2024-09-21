package com.multishop.fusiontech.dtos.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAddDto {
    private String comment;
    private int rating;
    private Long productId;
}
