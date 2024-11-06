package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.review.ReviewCreateDto;
import com.multishop.fusiontech.dtos.review.ReviewDto;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> getReviewsByProductId(Long productId);

    boolean createReview(ReviewCreateDto reviewCreateDto, String userEmail);

}
