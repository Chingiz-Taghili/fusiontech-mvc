package com.multishop.fusiontech.controllers;

import com.multishop.fusiontech.dtos.category.CategoryDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.common.AppealDto;
import com.multishop.fusiontech.dtos.common.TestimonialDto;
import com.multishop.fusiontech.dtos.slider.SliderDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        List<SliderDto> sliders = sliderService.getAllSliders();
        model.addAttribute("sliders", sliders);

        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<ProductDto> featuredProducts = productService.getFeaturedProducts().stream().limit(8).toList();
        model.addAttribute("featuredProducts", featuredProducts);

        List<ProductDto> offeredProducts = productService.getOfferedProducts().stream().limit(2).toList();
        model.addAttribute("offeredProducts", offeredProducts);

        List<TestimonialDto> testimonials = testimonialService.getAllTestimonials();
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

        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage(Model model, Principal principal) {

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

        return "contact";
    }

    @PostMapping("/contact")
    public String createAppeal(AppealDto appealDto) {
        boolean result = appealService.createAppeal(appealDto);
        if (!result) {
            return "redirect:/contact";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchProduct(String keyword, Model model, Principal principal, Integer currentPage) {

        PaginationPayload<ProductDto> products = productService.getSearchProducts(keyword, currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);

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

        return "search";
    }
}
