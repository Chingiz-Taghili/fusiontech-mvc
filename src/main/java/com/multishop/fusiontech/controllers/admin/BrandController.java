package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.brand.BrandCreateDto;
import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.brand.BrandUpdateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.models.Brand;
import com.multishop.fusiontech.services.BrandService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BrandController {

    private final BrandService brandService;
    private final UserService userService;

    public BrandController(BrandService brandService, UserService userService) {
        this.brandService = brandService;
        this.userService = userService;
    }

    @GetMapping("/admin/brand")
    public String showIndexPage(Model model, Principal principal) {
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("searchUrl", "/admin/search/brand");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/brand/index";
    }

    @GetMapping("/admin/search/brand")
    public String showSearchPage(String keyword, Model model, Principal principal) {
        model.addAttribute("brands", brandService.getSearchBrands(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchUrl", "/admin/search/brand");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/brand/index";
    }

    @GetMapping("/admin/brand/create")
    public String showCreatePage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("searchUrl", "/admin/search/brand");
        return "/admin/brand/create";
    }

    @PostMapping("/admin/brand/create")
    public String createBrand(BrandCreateDto brandCreateDto) {
        boolean result = brandService.createBrand(brandCreateDto);
        if (result) {
            return "redirect:/admin/brand";
        }
        return "redirect:/admin/brand/create";
    }

    @GetMapping("/admin/brand/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("brand", brandService.getBrandById(id));
        model.addAttribute("searchUrl", "/admin/search/brand");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/brand/update";
    }

    @PostMapping("/admin/brand/update/{id}")
    public String updateBrand(BrandUpdateDto brandUpdateDto, @PathVariable Long id) {
        boolean result = brandService.updateBrand(id, brandUpdateDto);
        if (result) {
            return "redirect:/admin/brand";
        }
        return "redirect:/admin/brand/update/" + id;
    }

    @GetMapping("admin/brand/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("brand", brandService.getBrandById(id));
        model.addAttribute("searchUrl", "/admin/search/brand");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/brand/delete";
    }

    @PostMapping("/admin/brand/delete/{id}")
    public String deleteBrand(@RequestParam Long brandId) {
        brandService.deleteBrand(brandId);
        return "redirect:/admin/brand";
    }
}
