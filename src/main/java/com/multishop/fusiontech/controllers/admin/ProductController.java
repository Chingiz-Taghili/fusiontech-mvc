package com.multishop.fusiontech.controllers.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.multishop.fusiontech.dtos.product.ProductCreateDto;
import com.multishop.fusiontech.dtos.product.ProductUpdateDto;
import com.multishop.fusiontech.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SubcategoryService subcategoryService;
    private final UserService userService;
    private final Cloudinary cloudinary;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService, SubcategoryService subcategoryService, UserService userService, Cloudinary cloudinary) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.subcategoryService = subcategoryService;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/admin/product")
    public String showIndexPage(Integer currentPage, Model model, Principal principal) {
        model.addAttribute("products", productService.getAllProducts(currentPage));
        model.addAttribute("urlType", "index");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/product/index";
    }

    @GetMapping("/admin/search/product")
    public String showSearchPage(String keyword, Integer currentPage, Model model, Principal principal) {
        model.addAttribute("products", productService.getSearchProducts(keyword, currentPage));
        model.addAttribute("urlType", "search");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/product/index";
    }

    @GetMapping("/admin/product/create")
    public String showCreatePage(Model model, Principal principal) {
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/product/create";
    }

//    @PostMapping("dashboard/logo/create")
//    public String create(LogoCreateDto lg, @RequestParam("image") MultipartFile file) throws IOException {
//        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//        lg.setPhotoUrl((String) uploadResult.get("url"));
//        logoService.createLogo(lg);
//        return "redirect:/dashboard/logo";
//    }

    @PostMapping("/admin/product/create")
    public String createNewProduct(ProductCreateDto productCreateDto, @RequestParam("images") List<MultipartFile> images) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            imageUrls.add((String) uploadResult.get("url"));
        }
        productCreateDto.setImageUrls(imageUrls);

        boolean result = productService.createProduct(productCreateDto);
        if (result) {
            return "redirect:/admin/product";
        } else {
            return "redirect:/admin/product/create";
        }
    }

    @GetMapping("/admin/product/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

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
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }
}
