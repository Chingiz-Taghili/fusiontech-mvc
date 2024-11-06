package com.multishop.fusiontech.controllers;

import com.multishop.fusiontech.dtos.category.CategoryDto;
import com.multishop.fusiontech.services.CategoryService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TermsController {

    private final CategoryService categoryService;
    private final UserService userService;

    public TermsController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/site-rules")
    public String showSiteRulesPage(Model model, Principal principal) {

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

        return "/terms/site-rules";
    }

    @GetMapping("/privacy")
    public String showPrivacyPage(Model model, Principal principal) {

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

        return "/terms/privacy";
    }

    @GetMapping("/corporate")
    public String showCorporatePage(Model model, Principal principal) {

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

        return "/terms/corporate";
    }

    @GetMapping("/price-policy")
    public String showPricePolicyPage(Model model, Principal principal) {

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

        return "/terms/price-policy";
    }

    @GetMapping("/refund")
    public String showRefundPage(Model model, Principal principal) {

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

        return "/terms/refund";
    }

    @GetMapping("/installment")
    public String showInstallmentPage(Model model, Principal principal) {

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

        return "/terms/installment";
    }

    @GetMapping("/delivery-payment")
    public String showDeliveryPaymentPage(Model model, Principal principal) {

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

        return "/terms/delivery-payment";
    }

    @GetMapping("/warranty")
    public String showWarrantyPage(Model model, Principal principal) {

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

        return "/terms/warranty";
    }
}
