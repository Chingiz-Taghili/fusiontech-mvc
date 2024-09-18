package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySubcategoryId(Long subcategoryId);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByPriceBetweenAndCategoryId(double minPrice, double maxPrice, Long categoryId);

    List<Product> findByPriceBetweenAndBrandId(double minPrice, double maxPrice, Long brandId);

    List<Product> findByPriceBetweenAndCategoryIdAndBrandId(
            double minPrice, double maxPrice, Long categoryId, Long brandId);

    List<Product> findByFeaturedTrue();

    List<Product> findByOfferedTrue();
}
