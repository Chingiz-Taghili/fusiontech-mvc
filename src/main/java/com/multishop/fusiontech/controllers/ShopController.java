package com.multishop.fusiontech.controllers;

import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.cart.CartDto;
import com.multishop.fusiontech.dtos.cart.CartItemCreateDto;
import com.multishop.fusiontech.dtos.category.CategoryDto;
import com.multishop.fusiontech.dtos.order.OrderCreateDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.review.ReviewCreateDto;
import com.multishop.fusiontech.dtos.review.ReviewDto;
import com.multishop.fusiontech.dtos.singledtos.ShopFilterDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.enums.PaymentMethod;
import com.multishop.fusiontech.models.Region;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ShopController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final BrandService brandService;
    private final CartItemService cartItemService;
    private final FavoritesService favoritesService;
    private final UserService userService;
    private final OrderService orderService;
    private final RegionService regionService;

    public ShopController(CategoryService categoryService, ProductService productService, ReviewService reviewService, BrandService brandService, CartItemService cartItemService, FavoritesService favoritesService, UserService userService, OrderService orderService, RegionService regionService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.reviewService = reviewService;
        this.brandService = brandService;
        this.cartItemService = cartItemService;
        this.favoritesService = favoritesService;
        this.userService = userService;
        this.orderService = orderService;
        this.regionService = regionService;
    }

    @GetMapping("/shop")
    public String showShopPage(Integer currentPage, Model model, Principal principal) {

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("totalCount", productService.getTotalProductCount());
        model.addAttribute("countByPrice", productService.getCountByPriceRanges());

        model.addAttribute("products", productService.getAllProducts(currentPage));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("urlType", "main");

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/index";
    }

    @GetMapping("/shop/filter")
    public String filterShopProducts(ShopFilterDto shopFilterDto, Model model, Principal principal, Integer currentPage) {

        if (shopFilterDto.getPrice() == 0 && shopFilterDto.getCategory() == 0 && shopFilterDto.getBrand() == 0) {
            return "redirect:/shop";
        }

        PaginationPayload<ProductDto> products = productService.getFilteredProducts(
                shopFilterDto.getPrice(), shopFilterDto.getCategory(), shopFilterDto.getBrand(), currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("filterDto", shopFilterDto);
        model.addAttribute("urlType", "filter");

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("totalCount", productService.getTotalProductCount());
        model.addAttribute("countByPrice", productService.getCountByPriceRanges());

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/index";
    }

    @GetMapping("/shop/{categoryId}/{subcategoryId}")
    public String filterFromCategory(@PathVariable Long categoryId, @PathVariable Long subcategoryId, Model model, Principal principal, Integer currentPage) {

        PaginationPayload<ProductDto> products = productService.getCatalogProducts(categoryId, subcategoryId, currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("subcategoryId", subcategoryId);
        model.addAttribute("urlType", "catalog");

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("totalCount", productService.getTotalProductCount());
        model.addAttribute("countByPrice", productService.getCountByPriceRanges());

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/index";
    }

    @GetMapping("/detail/{id}")
    public String showDetailPage(@PathVariable Long id, Model model, Principal principal) {

        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);

        List<ReviewDto> reviews = reviewService.getReviewsByProductId(id);
        model.addAttribute("reviews", reviews);

        List<ProductDto> relatedProducts =
                productService.getRelatedProducts(product.getCategory().getId(), product.getId());
        model.addAttribute("relatedProducts", relatedProducts);

        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/detail";
    }

    @PostMapping("/review")
    public String writeReview(ReviewCreateDto reviewCreateDto, Principal principal) {

        reviewService.createReview(reviewCreateDto, principal.getName());

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCartPage(Model model, Principal principal) {
        CartDto userCart = userService.getUserCart(principal.getName());
        model.addAttribute("cart", userCart);

        boolean active = true;
        if (userCart.getTotal() == 0) {
            active = false;
        }
        model.addAttribute("active", active);

        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/cart";
    }

    @GetMapping("/cart/{productId}")
    public String addOneToCart(@PathVariable Long productId, Principal principal) {
        CartItemCreateDto cartItemCreateDto = new CartItemCreateDto(productId, 1);
        cartItemService.addToCart(cartItemCreateDto, principal.getName());
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(CartItemCreateDto cartItemCreateDto, Principal principal) {
        cartItemService.addToCart(cartItemCreateDto, principal.getName());
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeCartItem(Long productId, Principal principal) {
        cartItemService.deleteCartItem(productId, principal.getName());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, Principal principal) {
        CartDto userCart = userService.getUserCart(principal.getName());
        if (userCart.getTotal() == 0) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", userCart);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        List<Region> regions = regionService.getRegions();
        model.addAttribute("regions", regions);

        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/checkout";
    }

    @PostMapping("/checkout")
    public String createOrder(OrderCreateDto orderCreateDto, Principal principal) {
        CartDto userCart = userService.getUserCart(principal.getName());
        if (userCart.getTotal() == 0) {
            return "redirect:/cart";
        }

        boolean result = orderService.createOrder(principal.getName(), orderCreateDto);
        if (!result) {
            return "redirect:/checkout";
        }

        if (orderCreateDto.getPaymentMethod() == PaymentMethod.ONLINE) {
            return "redirect:/cart";
        } else if (orderCreateDto.getPaymentMethod() == PaymentMethod.CASH) {
            return "redirect:/";

        }
        return "redirect:/cart";
    }

    @GetMapping("/favorites")
    public String showFavoritesPage(Model model, Principal principal, Integer currentPage) {

        PaginationPayload<ProductDto> products = userService.getUserFavoriteProducts(principal.getName(), currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);

        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        int cartSize;
        int favoriteSize;
        if (principal == null) {
            cartSize = 0;
            favoriteSize = 0;
        } else {
            cartSize = userService.getUserCartSize(principal.getName());
            favoriteSize = userService.getUserFavoriteSize(principal.getName());
        }
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("favoriteSize", favoriteSize);

        return "/shop/favorites";
    }

    @GetMapping("/favorites/add/{productId}")
    public String addToFavorites(@PathVariable Long productId, Principal principal) {
        favoritesService.addToFavorites(productId, principal.getName());
        return "redirect:/favorites";
    }

    @GetMapping("/favorites/remove/{productId}")
    public String removeFromFavorites(@PathVariable Long productId, Principal principal) {
        favoritesService.deleteFavorite(productId, principal.getName());
        return "redirect:/favorites";
    }
}
