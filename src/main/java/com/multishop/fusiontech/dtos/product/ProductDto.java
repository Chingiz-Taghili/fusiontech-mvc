package com.multishop.fusiontech.dtos.product;

import com.multishop.fusiontech.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String moreDetail;
    private double discountPrice;
    private double discountPercent;
    private LocalDateTime discountDate;
    private String formattedDiscountDate;
    private boolean featured;
    private boolean offered;
    private double rating;
    private List<Image> images;
    private String image;
    private List<Review> reviews;
    private Brand brand;
    private Category category;
    private Subcategory subcategory;
}
