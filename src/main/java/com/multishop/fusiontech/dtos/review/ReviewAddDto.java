package com.multishop.fusiontech.dtos.review;

import com.multishop.fusiontech.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAddDto {
    private String comment;
    private int rating;
    private Long productId;
}
