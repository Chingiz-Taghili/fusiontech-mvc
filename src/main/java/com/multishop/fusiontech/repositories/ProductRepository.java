package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findBySubcategoryId(Long subcategoryId, Pageable pageable);
    List<Product> findBySubcategoryId(Long subcategoryId);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String keyword);

    Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    Page<Product> findByPriceBetweenAndCategoryId(double minPrice, double maxPrice, Long categoryId, Pageable pageable);
    List<Product> findByPriceBetweenAndCategoryId(double minPrice, double maxPrice, Long categoryId);

    Page<Product> findByPriceBetweenAndBrandId(double minPrice, double maxPrice, Long brandId, Pageable pageable);
    List<Product> findByPriceBetweenAndBrandId(double minPrice, double maxPrice, Long brandId);

    Page<Product> findByPriceBetweenAndCategoryIdAndBrandId(
            double minPrice, double maxPrice, Long categoryId, Long brandId, Pageable pageable);
    List<Product> findByPriceBetweenAndCategoryIdAndBrandId(
            double minPrice, double maxPrice, Long categoryId, Long brandId);

    List<Product> findByFeaturedTrue();

    List<Product> findByOfferedTrue();

    long countByPriceLessThanEqual(double price);

    long countByPriceBetween(double startPrice, double endPrice);

    long countByPriceGreaterThan(double price);
}
