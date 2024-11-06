package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.review.ReviewCreateDto;
import com.multishop.fusiontech.dtos.review.ReviewDto;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.Review;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.ProductRepository;
import com.multishop.fusiontech.repositories.ReviewRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.ProductService;
import com.multishop.fusiontech.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "writingDate"));
        List<Review> repoReviews = reviewRepository.findByProductId(productId, pageable);
        List<ReviewDto> productReviews = repoReviews.stream().map(review -> modelMapper.map(review, ReviewDto.class)).toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        for (int i = 0; i < productReviews.size(); i++) {
            if (repoReviews.get(i).getWritingDate() != null) {
                productReviews.get(i).setFormattedWritingDate(repoReviews.get(i).getWritingDate().format(formatter));
            }
        }

        return productReviews;
    }

    @Override
    public boolean createReview(ReviewCreateDto reviewCreateDto, String userEmail) {

        UserEntity findUser = userRepository.findByEmail(userEmail);
        if (findUser == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        Product findProduct = productRepository.findById(reviewCreateDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + reviewCreateDto.getProductId()));

        Review review = new Review();
        review.setUserName(findUser.getName());
        review.setUserSurname(findUser.getSurname());
        review.setUserImage(findUser.getImage() != null ? findUser.getImage() : "https://cdn.pixabay.com/photo/2014/05/24/00/07/woven-cloth-352481_640.jpg");
        review.setComment(reviewCreateDto.getComment());
        review.setRating(reviewCreateDto.getRating());
        review.setWritingDate(LocalDateTime.now());
        review.setProduct(findProduct);

        reviewRepository.save(review);

        productService.updateProductRating(reviewCreateDto.getProductId());

        return true;
    }
}
