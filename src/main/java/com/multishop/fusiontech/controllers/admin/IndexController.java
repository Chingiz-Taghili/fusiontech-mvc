package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    private final UserService userService;
    private final AppealService appealService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SliderService sliderService;

    public IndexController(UserService userService, AppealService appealService, BrandService brandService, CategoryService categoryService, OrderService orderService, ProductService productService, SliderService sliderService) {
        this.userService = userService;
        this.appealService = appealService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.productService = productService;
        this.sliderService = sliderService;
    }

    @GetMapping("/admin")
    public String showIndexPage(Principal principal, Model model) {
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("appealCount", appealService.getTotalCount());
        model.addAttribute("brandCount", brandService.getTotalCount());
        model.addAttribute("categoryCount", categoryService.getTotalCount());
        model.addAttribute("orderCount", orderService.getTotalCount());
        model.addAttribute("productCount", productService.getTotalProductCount());
        model.addAttribute("sliderCount", sliderService.getTotalCount());
        model.addAttribute("userCount", userService.getTotalUserCount());
        return "/admin/index";
    }
}
