package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.product.*;
import com.multishop.fusiontech.models.*;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.repositories.*;
import com.multishop.fusiontech.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ReviewRepository reviewRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public ProductDetailDto getProductDetail(Long id) {
        Product repoProduct = productRepository.findById(id).orElseThrow();
        ProductDetailDto productDetail = modelMapper.map(repoProduct, ProductDetailDto.class);
        return productDetail;
    }

    @Override
    public ProductUpdateDto getUpdatedProduct(Long id) {

        Product repoProduct = productRepository.findById(id).orElseThrow();
        ProductUpdateDto updatedProduct = modelMapper.map(repoProduct, ProductUpdateDto.class);

        List<String> images = new ArrayList<>();
        for (Image image : repoProduct.getImages()) {
            String imageUrl = image.getUrl();
            images.add(imageUrl);
        }
        updatedProduct.setImagesUrl(images);
        updatedProduct.setBrandId(repoProduct.getBrand().getId());
        updatedProduct.setCategoryId(repoProduct.getCategory().getId());
        updatedProduct.setSubcategoryId(repoProduct.getSubcategory().getId());

        return updatedProduct;
    }

    @Override
    public List<ProductRelatedDto> getRelatedProducts(Long categoryId, Long productId) {
        List<Product> repoProducts = productRepository.findByCategoryId(categoryId).stream().filter(product -> product.getId() != productId).limit(5).toList();
        List<ProductRelatedDto> relatedProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductRelatedDto.class)).toList();
        for (int i = 0; i < relatedProducts.size(); i++) {
            relatedProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return relatedProducts;
    }

    @Override
    public List<ProductFeaturedDto> getFeaturedProducts() {
        List<Product> repoProducts = productRepository.findByFeaturedTrue();
        List<ProductFeaturedDto> featureProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductFeaturedDto.class)).toList();
        for (int i = 0; i < featureProducts.size(); i++) {
            featureProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return featureProducts;
    }

    @Override
    public List<ProductOfferedDto> getOfferedProducts() {
        List<Product> repoProducts = productRepository.findByOfferedTrue();
        List<ProductOfferedDto> offerProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductOfferedDto.class)).toList();
        for (int i = 0; i < offerProducts.size(); i++) {
            double percent = (double) Math.round((repoProducts.get(i).getPrice() - repoProducts.get(i).getDiscountPrice()) / repoProducts.get(i).getPrice() * 100 * 100) / 100;
            offerProducts.get(i).setDiscountPercent(percent);
            offerProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return offerProducts;
    }

    @Override
    public List<ProductShopDto> getShopProducts() {
        List<Product> repoProducts = productRepository.findAll();
        List<ProductShopDto> shopProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductShopDto.class)).toList();

        for (int i = 0; i < shopProducts.size(); i++) {
            shopProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }

        return shopProducts;
    }

    @Override
    public PaginationPayload<ProductAdminDto> getAdminProducts(Integer pageNumber) {

        pageNumber = pageNumber == null ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductAdminDto> result = products.getContent().stream().map(product -> modelMapper.map(product, ProductAdminDto.class)).toList();

        PaginationPayload<ProductAdminDto> paginationPayload = new PaginationPayload<>();
        paginationPayload.setTotalPage(products.getTotalPages());
        paginationPayload.setCurrentPage(pageNumber);
        paginationPayload.setData(result);

        return paginationPayload;
    }

    @Override
    public List<ProductShopDto> getFilteredProducts(int price, int category, int brand) {

        List<Product> repoProducts;
        int priceMin = 0;
        int priceMax = Integer.MAX_VALUE;

        switch (price) {
            case 1:
                priceMax = 100;
                break;
            case 2:
                priceMin = 100;
                priceMax = 1000;
                break;
            case 3:
                priceMin = 1000;
                priceMax = 2000;
                break;
            case 4:
                priceMin = 2000;
                priceMax = 3000;
                break;
            case 5:
                priceMin = 3000;
                break;
        }

        if (category == 0 && brand == 0) {
            repoProducts = productRepository.findByPriceBetween(priceMin, priceMax);
        } else if (category != 0 && brand == 0) {
            repoProducts = productRepository.findByPriceBetweenAndCategoryId(priceMin, priceMax, (long) category);
        } else if (category == 0 && brand != 0) {
            repoProducts = productRepository.findByPriceBetweenAndBrandId(priceMin, priceMax, (long) brand);
        } else {
            repoProducts = productRepository.findByPriceBetweenAndCategoryIdAndBrandId(priceMin, priceMax, (long) category, (long) brand);
        }

        List<ProductShopDto> shopProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductShopDto.class)).toList();

        for (int i = 0; i < shopProducts.size(); i++) {
            shopProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }

        return shopProducts;
    }

    @Override
    public List<ProductShopDto> getCatalogProducts(Long categoryId, Long subcategoryId) {

        List<Product> repoProducts;

        if (categoryId != 0) {
            repoProducts = productRepository.findByCategoryId(categoryId);
        } else {
            repoProducts = productRepository.findBySubcategoryId(subcategoryId);
        }

        List<ProductShopDto> shopProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductShopDto.class)).toList();

        for (int i = 0; i < shopProducts.size(); i++) {
            shopProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }

        return shopProducts;
    }

    @Override
    public List<ProductShopDto> getSearchProducts(String keyword) {

        List<Product> repoProducts = productRepository.findByNameContainingIgnoreCase(keyword);

        List<ProductShopDto> searchProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductShopDto.class)).toList();

        for (int i = 0; i < searchProducts.size(); i++) {
            searchProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }

        return searchProducts;
    }

    @Override
    public void updateProductRating(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product not found with ID: " + productId));

        List<Review> reviews = reviewRepository.findByProductId(productId);
        Double newRating = reviews.isEmpty() ? null : reviews.stream()
                .mapToDouble(Review::getRating).average().orElse(0.0);
        product.setRating(newRating);
        productRepository.save(product);
    }

    @Override
    public boolean createProduct(ProductCreateDto productCreateDto) {

        Product newProduct = modelMapper.map(productCreateDto, Product.class);

        Brand brand = brandRepository.findById(productCreateDto.getBrand()).orElseThrow();
        Category category = categoryRepository.findById(productCreateDto.getCategory()).orElseThrow();
        Subcategory subcategory = subcategoryRepository.findById(productCreateDto.getSubcategory()).orElseThrow();
        newProduct.setBrand(brand);
        newProduct.setCategory(category);
        newProduct.setSubcategory(subcategory);

        List<Image> images = new ArrayList<>();
        for (String imageUrl : productCreateDto.getImages()) {
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setProduct(newProduct);
            images.add(image);
        }
        newProduct.setImages(images);

        productRepository.save(newProduct);

        return true;
    }

    @Override
    public boolean updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        try {
            Product findProduct = productRepository.findById(id).orElseThrow();
            findProduct.setName(productUpdateDto.getName());
            findProduct.setPrice(productUpdateDto.getPrice());
            findProduct.setDescription(productUpdateDto.getDescription());
            findProduct.setMoreDetail(productUpdateDto.getMoreDetail());
            findProduct.setDiscountPrice(productUpdateDto.getDiscountPrice());
            findProduct.setDiscountDate(productUpdateDto.getDiscountDate());
            findProduct.setFeatured(productUpdateDto.isFeatured());
            findProduct.setOffered(productUpdateDto.isOffered());

            List<Image> updatedImages = new ArrayList<>();
            for (String imageUrl : productUpdateDto.getImagesUrl()) {
                Image image = new Image();
                image.setProduct(findProduct);
                image.setUrl(imageUrl);
                updatedImages.add(image);
            }

            // Kolleksiyanı yeniləyir
            findProduct.getImages().clear(); // Köhnə şəkilləri silmək
            findProduct.getImages().addAll(updatedImages); // Yeni şəkilləri əlavə etmək

            findProduct.setBrand(brandRepository.findById(productUpdateDto.getBrandId()).orElseThrow());
            findProduct.setCategory(categoryRepository.findById(productUpdateDto.getCategoryId()).orElseThrow());
            findProduct.setSubcategory(subcategoryRepository.findById(productUpdateDto.getSubcategoryId()).orElseThrow());

            productRepository.save(findProduct);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
