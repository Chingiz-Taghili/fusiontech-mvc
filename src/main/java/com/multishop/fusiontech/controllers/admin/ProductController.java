package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.category.CategoryAdminDto;
import com.multishop.fusiontech.dtos.product.ProductAdminDto;
import com.multishop.fusiontech.dtos.product.ProductCreateDto;
import com.multishop.fusiontech.dtos.product.ProductDetailDto;
import com.multishop.fusiontech.dtos.product.ProductUpdateDto;
import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.singledtos.SubcategoryDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.BrandService;
import com.multishop.fusiontech.services.CategoryService;
import com.multishop.fusiontech.services.ProductService;
import com.multishop.fusiontech.services.SubcategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SubcategoryService subcategoryService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService, SubcategoryService subcategoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/admin/product")
    public String showIndexPage(Integer currentPage, Model model) {
        PaginationPayload<ProductAdminDto> products = productService.getAdminProducts(currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        return "/admin/product/index";
    }

    @GetMapping("/admin/product/create")
    public String showCreatePage(Model model) {

        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        List<CategoryAdminDto> categories = categoryService.getAdminCategories();
        model.addAttribute("categories", categories);
        List<SubcategoryDto> subcategories = subcategoryService.getAllSubcategories();
        model.addAttribute("subcategories", subcategories);

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
    public String showUpdatePage(@PathVariable Long id, Model model) {

        ProductUpdateDto product = productService.getUpdatedProduct(id);
        model.addAttribute("product", product);
        List<BrandDto> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        List<CategoryAdminDto> categories = categoryService.getAdminCategories();
        model.addAttribute("categories", categories);
        List<SubcategoryDto> subcategories = subcategoryService.getAllSubcategories();
        model.addAttribute("subcategories", subcategories);

        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, ProductUpdateDto productUpdateDto) {

        boolean result = productService.updateProduct(id, productUpdateDto);

        if (result) {
            return "redirect:/admin/product";
        } else {
            return "redirect:/admin/product/update";
        }
    }

    @GetMapping("/admin/product/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model) {

        ProductDetailDto product = productService.getProductDetail(id);
        model.addAttribute("product", product);

        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }
}
