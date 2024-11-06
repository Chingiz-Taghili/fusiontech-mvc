package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.category.CategoryDto;
import com.multishop.fusiontech.dtos.product.ProductCreateDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.product.ProductUpdateDto;
import com.multishop.fusiontech.dtos.singledtos.SubcategoryDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SubcategoryService subcategoryService;
    private final UserService userService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService, SubcategoryService subcategoryService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.subcategoryService = subcategoryService;
        this.userService = userService;
    }

    @GetMapping("/admin/product")
    public String showIndexPage(Integer currentPage, Model model, Principal principal) {
        PaginationPayload<ProductDto> products = productService.getAllProducts(currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchUrl", "/admin/search/product");

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/product/index";
    }

    @GetMapping("/admin/product/create")
    public String showCreatePage(Model model, Principal principal) {

        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<SubcategoryDto> subcategories = subcategoryService.getAllSubcategories();
        model.addAttribute("subcategories", subcategories);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createNewProduct(ProductCreateDto productCreateDto) {

        boolean result = productService.createProduct(productCreateDto);

        if (result) {
            return "redirect:/admin/product";
        } else {
            return "redirect:/admin/product/create";
        }
    }

    @GetMapping("/admin/product/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {

        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<SubcategoryDto> subcategories = subcategoryService.getAllSubcategories();
        model.addAttribute("subcategories", subcategories);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, ProductUpdateDto productUpdateDto) {

        boolean result = productService.updateProduct(id, productUpdateDto);

        if (result) {
            return "redirect:/admin/product";
        } else {
            return "redirect:/admin/product/update/" + id;
        }
    }

    @GetMapping("/admin/product/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {

        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }
}
