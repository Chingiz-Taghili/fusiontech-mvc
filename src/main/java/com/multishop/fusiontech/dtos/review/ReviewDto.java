package com.multishop.fusiontech.dtos.review;

import com.multishop.fusiontech.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String userName;
    private String userSurname;
    private String userImage;
    private String comment;
    private int rating;
    private LocalDateTime writingDate;
    private String formattedWritingDate;
    private Product product;
}
