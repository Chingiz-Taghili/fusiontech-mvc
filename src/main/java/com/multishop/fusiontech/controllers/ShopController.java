package com.multishop.fusiontech.controllers;

import com.multishop.fusiontech.dtos.category.CategoryLayoutDto;
import com.multishop.fusiontech.dtos.category.CategoryShopDto;
import com.multishop.fusiontech.dtos.product.ProductShopDto;
import com.multishop.fusiontech.dtos.review.ReviewAddDto;
import com.multishop.fusiontech.dtos.singledtos.BasketAddDto;
import com.multishop.fusiontech.dtos.order.OrderPlaceDto;
import com.multishop.fusiontech.dtos.product.ProductDetailDto;
import com.multishop.fusiontech.dtos.product.ProductRelatedDto;
import com.multishop.fusiontech.dtos.singledtos.BrandDto;
import com.multishop.fusiontech.dtos.singledtos.ShopFilteredDto;
import com.multishop.fusiontech.dtos.singledtos.UserCartDto;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.Region;
import com.multishop.fusiontech.models.UserEntity;
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
    private final BasketService basketService;
    private final FavoritesService favoritesService;
    private final UserService userService;
    private final OrderService orderService;
    private final RegionService regionService;

    public ShopController(CategoryService categoryService, ProductService productService, ReviewService reviewService, BrandService brandService, BasketService basketService, FavoritesService favoritesService, UserService userService, OrderService orderService, RegionService regionService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.reviewService = reviewService;
        this.brandService = brandService;
        this.basketService = basketService;
        this.favoritesService = favoritesService;
        this.userService = userService;
        this.orderService = orderService;
        this.regionService = regionService;
    }

    @GetMapping("/shop")
    public String showShopPage(Model model, Principal principal) {

        List<CategoryShopDto> categories = categoryService.getShopCategories();
        model.addAttribute("categories", categories);

        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);

        List<ProductShopDto> products = productService.getShopProducts();
        model.addAttribute("products", products);

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
    public String filterShopProducts(ShopFilteredDto shopFilteredDto, Model model, Principal principal) {

        if (shopFilteredDto.getPrice() == 0 && shopFilteredDto.getCategory() == 0 && shopFilteredDto.getBrand() == 0) {
            return "redirect:/shop";
        }

        List<ProductShopDto> products = productService.getFilteredProducts(shopFilteredDto.getPrice(), shopFilteredDto.getCategory(), shopFilteredDto.getBrand());
        model.addAttribute("products", products);

        List<CategoryShopDto> categories = categoryService.getShopCategories();
        model.addAttribute("categories", categories);

        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);

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
    public String filterFromCategory(@PathVariable Long categoryId, @PathVariable Long subcategoryId, Model model, Principal principal) {

        List<ProductShopDto> products = productService.getCatalogProducts(categoryId, subcategoryId);
        model.addAttribute("products", products);

        List<CategoryShopDto> categories = categoryService.getShopCategories();
        model.addAttribute("categories", categories);

        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);

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

        ProductDetailDto product = productService.getProductDetail(id);
        model.addAttribute("product", product);

        List<ProductRelatedDto> relatedProducts =
                productService.getRelatedProducts(product.getCategory().getId(), product.getId());
        model.addAttribute("relatedProducts", relatedProducts);

        List<CategoryLayoutDto> categories = categoryService.getLayoutCategories();
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
    public String writeReview(ReviewAddDto reviewAddDto, Principal principal) {

        reviewService.writeReview(reviewAddDto, principal.getName());

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCartPage(Model model, Principal principal) {
        UserCartDto userCart = userService.getUserCart(principal.getName());
        model.addAttribute("cart", userCart);

        boolean active = true;
        if (userCart.getTotal() == 0) {
            active = false;
        }
        model.addAttribute("active", active);

        List<CategoryLayoutDto> categories = categoryService.getLayoutCategories();
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
        BasketAddDto basketAddDto = new BasketAddDto(productId, 1L);
        basketService.addToCart(basketAddDto, principal.getName());
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(BasketAddDto basketAddDto, Principal principal) {
        basketService.addToCart(basketAddDto, principal.getName());
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeBasket(Long productId, Principal principal) {
        basketService.removeBasket(productId, principal.getName());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, Principal principal) {
        UserCartDto userCart = userService.getUserCart(principal.getName());
        if (userCart.getTotal() == 0) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", userCart);

        UserEntity user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        List<Region> regions = regionService.getRegions();
        model.addAttribute("regions", regions);

        List<CategoryLayoutDto> categories = categoryService.getLayoutCategories();
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
    public String placeOrder(OrderPlaceDto orderPlaceDto, Principal principal) {
        UserCartDto userCart = userService.getUserCart(principal.getName());
        if (userCart.getTotal() == 0) {
            return "redirect:/cart";
        }

        boolean result = orderService.checkout(principal.getName(), orderPlaceDto);
        if (!result) {
            return "redirect:/checkout";
        }

        if (orderPlaceDto.getPaymentMethod() == 0) {
            return "redirect:/cart";
        } else if (orderPlaceDto.getPaymentMethod() == 1) {
            return "redirect:/";

        }
        return "redirect:/cart";
    }

    @GetMapping("/favorites")
    public String showFavoritesPage(Model model, Principal principal) {

        List<ProductShopDto> products = userService.getUserFavoriteProducts(principal.getName());
        model.addAttribute("products", products);

        List<CategoryLayoutDto> categories = categoryService.getLayoutCategories();
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
        favoritesService.removeFromFavorites(productId, principal.getName());
        return "redirect:/favorites";
    }
}
