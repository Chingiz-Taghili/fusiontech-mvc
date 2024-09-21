package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.review.ReviewAddDto;
import com.multishop.fusiontech.dtos.review.ReviewShowDto;

import java.util.List;

public interface ReviewService {

    List<ReviewShowDto> getReviews(Long productId);

    boolean writeReview(ReviewAddDto reviewAddDto, String userEmail);

}
