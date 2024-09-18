package com.multishop.fusiontech.controllers;

import com.multishop.fusiontech.dtos.category.CategoryHomeDto;
import com.multishop.fusiontech.dtos.category.CategoryLayoutDto;
import com.multishop.fusiontech.dtos.product.ProductFeaturedDto;
import com.multishop.fusiontech.dtos.product.ProductOfferedDto;
import com.multishop.fusiontech.dtos.product.ProductShopDto;
import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.dtos.singledtos.SliderHomeDto;
import com.multishop.fusiontech.dtos.singledtos.TestimonialDto;
import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final SliderService sliderService;
    private final TestimonialService testimonialService;
    private final AppealService appealService;

    public HomeController(UserService userService, CategoryService categoryService, ProductService productService, SliderService sliderService, TestimonialService testimonialService, AppealService appealService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.sliderService = sliderService;
        this.testimonialService = testimonialService;
        this.appealService = appealService;
    }

    @GetMapping("/")
    public String showHomePage(Model model, Principal principal) {

        List<SliderHomeDto> sliders = sliderService.getHomeSliders();
        model.addAttribute("sliders", sliders);

        List<CategoryHomeDto> categories = categoryService.getHomeCategories();
        model.addAttribute("categories", categories);

        List<ProductFeaturedDto> featuredProducts = productService.getFeaturedProducts().stream().limit(8).toList();
        model.addAttribute("featuredProducts", featuredProducts);

        List<ProductOfferedDto> offeredProducts = productService.getOfferedProducts().stream().limit(2).toList();
        model.addAttribute("offeredProducts", offeredProducts);

        List<TestimonialDto> testimonials = testimonialService.getTestimonials();
        model.addAttribute("testimonials", testimonials);

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

        return "home";
    }

    @GetMapping("/about")
    public String showAboutPage(Model model, Principal principal) {

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

        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage(Model model, Principal principal) {

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

        return "contact";
    }

    @PostMapping("/contact")
    public String createAppeal(AppealDto appealDto) {
        boolean result = appealService.placeAppeal(appealDto);
        if (!result) {
            return "redirect:/contact";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model, Principal principal) {

        List<ProductShopDto> products = productService.getSearchProducts(keyword);
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

        return "search";
    }
}
