package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.product.ProductCreateDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.product.ProductUpdateDto;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean createProduct(ProductCreateDto productCreateDto) {

        Product newProduct = modelMapper.map(productCreateDto, Product.class);

        Brand brand = brandRepository.findById(productCreateDto.getBrand()).orElseThrow();
        Category category = categoryRepository.findById(productCreateDto.getCategory()).orElseThrow();
        Subcategory subcategory = subcategoryRepository.findById(productCreateDto.getSubcategory()).orElseThrow();
        newProduct.setBrand(brand);
        newProduct.setCategory(category);
        newProduct.setSubcategory(subcategory);

        List<Image> images = new ArrayList<>();
        for (String url : productCreateDto.getImageUrls()) {
            Image image = new Image();
            image.setUrl(url);
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

    @Override
    public ProductDto getProductById(Long id) {
        Product repoProduct = productRepository.findById(id).orElseThrow();
        ProductDto product = modelMapper.map(repoProduct, ProductDto.class);
        product.setImage(repoProduct.getImages().get(0).getUrl());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        if (repoProduct.getDiscountDate() != null) {
            product.setFormattedDiscountDate(repoProduct.getDiscountDate().format(formatter));
        }
        return product;
    }

    @Override
    public PaginationPayload<ProductDto> getAllProducts(Integer pageNumber) {

        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        Page<Product> repoProducts = productRepository.findAll(pageable);

        List<ProductDto> products = repoProducts.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (int i = 0; i < products.size(); i++) {
            if (repoProducts.getContent().get(i).getDiscountDate() != null) {
                products.get(i).setFormattedDiscountDate(repoProducts.getContent().get(i)
                        .getDiscountDate().format(formatter));
            }
            products.get(i).setImage(repoProducts.getContent().get(i).getImages().get(0).getUrl());
        }

        PaginationPayload<ProductDto> paginationProducts = new PaginationPayload<>();
        paginationProducts.setTotalPage(repoProducts.getTotalPages());
        paginationProducts.setCurrentPage(pageNumber);
        paginationProducts.setData(products);

        return paginationProducts;
    }

    @Override
    public PaginationPayload<ProductDto> getFilteredProducts(int price, int category, int brand, Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));

        Page<Product> repoProducts;
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
            repoProducts = productRepository.findByPriceBetween(priceMin, priceMax, pageable);
        } else if (category != 0 && brand == 0) {
            repoProducts = productRepository.findByPriceBetweenAndCategoryId(priceMin, priceMax, (long) category, pageable);
        } else if (category == 0 && brand != 0) {
            repoProducts = productRepository.findByPriceBetweenAndBrandId(priceMin, priceMax, (long) brand, pageable);
        } else {
            repoProducts = productRepository.findByPriceBetweenAndCategoryIdAndBrandId(priceMin, priceMax, (long) category, (long) brand, pageable);
        }

        List<ProductDto> shopProducts = repoProducts.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        for (int i = 0; i < shopProducts.size(); i++) {
            shopProducts.get(i).setImage(repoProducts.getContent().get(i).getImages().get(0).getUrl());
        }

        PaginationPayload<ProductDto> paginationProducts = new PaginationPayload<>
                (repoProducts.getTotalPages(), pageNumber, shopProducts);

        return paginationProducts;
    }

    @Override
    public PaginationPayload<ProductDto> getCatalogProducts(Long categoryId, Long subcategoryId, Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));

        Page<Product> repoProducts;

        if (categoryId != 0) {
            repoProducts = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            repoProducts = productRepository.findBySubcategoryId(subcategoryId, pageable);
        }

        List<ProductDto> products = repoProducts.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        for (int i = 0; i < products.size(); i++) {
            products.get(i).setImage(repoProducts.getContent().get(i).getImages().get(0).getUrl());
        }

        PaginationPayload<ProductDto> paginationProducts = new PaginationPayload<>(repoProducts.getTotalPages(), pageNumber, products);

        return paginationProducts;
    }

    @Override
    public PaginationPayload<ProductDto> getSearchProducts(String keyword, Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));

        Page<Product> repoProducts = productRepository.findByKeywordInColumnsIgnoreCase(keyword, pageable);

        List<ProductDto> searchProducts = repoProducts.getContent()
                .stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (int i = 0; i < searchProducts.size(); i++) {
            if (repoProducts.getContent().get(i).getDiscountDate() != null) {
                searchProducts.get(i).setFormattedDiscountDate(
                        repoProducts.getContent().get(i).getDiscountDate().format(formatter));
            }
            searchProducts.get(i).setImage(repoProducts.getContent().get(i).getImages().get(0).getUrl());
        }

        PaginationPayload<ProductDto> paginationProducts = new PaginationPayload<>(
                        repoProducts.getTotalPages(), pageNumber, searchProducts);

        return paginationProducts;
    }

    @Override
    public List<ProductDto> getRelatedProducts(Long categoryId, Long productId) {
        List<Product> repoProducts = productRepository.findByCategoryId(categoryId).stream().filter(product -> product.getId() != productId).limit(5).toList();
        List<ProductDto> relatedProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        for (int i = 0; i < relatedProducts.size(); i++) {
            relatedProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return relatedProducts;
    }

    @Override
    public List<ProductDto> getFeaturedProducts() {
        List<Product> repoProducts = productRepository.findByFeaturedTrue();
        List<ProductDto> featureProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        for (int i = 0; i < featureProducts.size(); i++) {
            featureProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return featureProducts;
    }

    @Override
    public List<ProductDto> getOfferedProducts() {
        List<Product> repoProducts = productRepository.findByOfferedTrue();
        List<ProductDto> offerProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        for (int i = 0; i < offerProducts.size(); i++) {
            double percent = (double) Math.round((repoProducts.get(i).getPrice() - repoProducts.get(i).getDiscountPrice()) / repoProducts.get(i).getPrice() * 100 * 100) / 100;
            offerProducts.get(i).setDiscountPercent(percent);
            offerProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }
        return offerProducts;
    }

    @Override
    public Long getTotalProductCount() {
        return productRepository.count();
    }

    @Override
    public Map<Integer, Long> getCountByPriceRanges() {
        Map<Integer, Long> countByPriceMap = new HashMap<>();
        countByPriceMap.put(1, productRepository.countByPriceLessThanEqual(100));
        countByPriceMap.put(2, productRepository.countByPriceBetween(100, 1000));
        countByPriceMap.put(3, productRepository.countByPriceBetween(1000, 2000));
        countByPriceMap.put(4, productRepository.countByPriceBetween(2000, 3000));
        countByPriceMap.put(5, productRepository.countByPriceGreaterThan(3000));

        return countByPriceMap;
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
}
