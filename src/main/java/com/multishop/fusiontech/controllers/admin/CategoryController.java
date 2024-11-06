package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.category.CategoryCreateDto;
import com.multishop.fusiontech.dtos.category.CategoryUpdateDto;
import com.multishop.fusiontech.services.CategoryService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/admin/category")
    public String showIndexPage(Model model, Principal principal) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("searchUrl", "/admin/search/category");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/category/index";
    }

    @GetMapping("/admin/search/category")
    public String showSearchPage(String keyword, Model model, Principal principal) {
        model.addAttribute("categories", categoryService.getSearchCategories(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchUrl", "/admin/search/category");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/category/index";
    }

    @GetMapping("/admin/category/create")
    public String showCreatePage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("searchUrl", "/admin/search/category");
        return "/admin/category/create";
    }

    @PostMapping("/admin/category/create")
    public String createCategory(CategoryCreateDto categoryCreateDto) {
        boolean result = categoryService.createCategory(categoryCreateDto);
        if (result) {
            return "redirect:/admin/category";
        }
        return "redirect:/admin/category/create";
    }

    @GetMapping("/admin/category/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("searchUrl", "/admin/search/category");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/category/update";
    }

    @PostMapping("/admin/category/update/{id}")
    public String updateCategory(CategoryUpdateDto categoryUpdateDto, @PathVariable Long id) {
        boolean result = categoryService.updateCategory(id, categoryUpdateDto);
        if (result) {
            return "redirect:/admin/category";
        }
        return "redirect:/admin/category/update/" + id;
    }

    @GetMapping("admin/category/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("searchUrl", "/admin/search/category");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/category/delete";
    }

    @PostMapping("/admin/category/delete/{id}")
    public String deleteCategory(@RequestParam Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/category";
    }
}
