package com.multishop.fusiontech.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.multishop.fusiontech.dtos.category.CategoryDto;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.services.CategoryService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final Cloudinary cloudinary;

    public AuthController(UserService userService, CategoryService categoryService, Cloudinary cloudinary) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, Principal principal) {

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

        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model, Principal principal) {

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

        return "auth/register";
    }

    @PostMapping("/register")
    public String createAccount(UserCreateDto userCreateDto, @RequestParam MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            userCreateDto.setImage((String) uploadResult.get("url"));
        }
        boolean result = userService.createUser(userCreateDto);
        if (result) {
            return "redirect:/login";
        }
        return "redirect:/register";
    }
}
