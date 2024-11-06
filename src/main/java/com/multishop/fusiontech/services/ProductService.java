package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.product.ProductCreateDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.product.ProductUpdateDto;
import com.multishop.fusiontech.payloads.PaginationPayload;

import java.util.List;
import java.util.Map;

public interface ProductService {

    boolean createProduct(ProductCreateDto productCreateDto);

    boolean updateProduct(Long id, ProductUpdateDto productUpdateDto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    PaginationPayload<ProductDto> getAllProducts(Integer pageNumber);

    PaginationPayload<ProductDto> getFilteredProducts(int price, int category, int brand, Integer pageNumber);

    PaginationPayload<ProductDto> getCatalogProducts(Long categoryId, Long subcategoryId, Integer pageNumber);

    PaginationPayload<ProductDto> getSearchProducts(String keyword, Integer pageNumber);

    List<ProductDto> getFeaturedProducts();

    List<ProductDto> getRelatedProducts(Long categoryId, Long productId);

    List<ProductDto> getOfferedProducts();

    Long getTotalProductCount();

    Map<Integer, Long> getCountByPriceRanges();

    void updateProductRating(Long productId);

}
