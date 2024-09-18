package com.multishop.fusiontech.dtos.review;

import com.multishop.fusiontech.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewShowDto {
    private Long id;
    private String userName;
    private String userSurname;
    private String userImage;
    private String comment;
    private double rating;
    private Date writingDate;
    private Product product;
}
