package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.review.ReviewAddDto;
import com.multishop.fusiontech.dtos.review.ReviewShowDto;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.Review;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.ProductRepository;
import com.multishop.fusiontech.repositories.ReviewRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.ProductService;
import com.multishop.fusiontech.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, ProductService productService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewShowDto> getReviews(Long productId) {
        List<Review> repoReviews = reviewRepository.findByProductId(productId);
        List<ReviewShowDto> productReviews = repoReviews.stream().map(review -> modelMapper.map(review, ReviewShowDto.class)).toList();
        return productReviews;
    }

    @Override
    public boolean writeReview(ReviewAddDto reviewAddDto, String userEmail) {

        UserEntity findUser = userRepository.findByEmail(userEmail);
        if (findUser == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        Product findProduct = productRepository.findById(reviewAddDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setUserName(findUser.getName());
        review.setUserSurname(findUser.getSurname());
        review.setUserImage(findUser.getImage() != null ? findUser.getImage() : "https://cdn.pixabay.com/photo/2014/05/24/00/07/woven-cloth-352481_640.jpg");
        review.setComment(reviewAddDto.getComment());
        review.setRating(reviewAddDto.getRating());
        review.setWritingDate(new Date());
        review.setProduct(findProduct);

        reviewRepository.save(review);

        productService.updateProductRating(reviewAddDto.getProductId());

        return true;
    }
}
