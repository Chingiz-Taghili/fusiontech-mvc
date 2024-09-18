package com.multishop.fusiontech.dtos.product;

import com.multishop.fusiontech.dtos.category.CategoryShopDto;
import com.multishop.fusiontech.models.Image;
import com.multishop.fusiontech.models.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String moreDetail;
    private double discountPrice;
    private double rating;
    private List<Image> images;
    private List<Review> reviews;

    private CategoryShopDto category;
}
