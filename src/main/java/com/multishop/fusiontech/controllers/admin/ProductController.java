package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.product.ProductAdminDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String index(Integer currentPage, Model model) {
        PaginationPayload<ProductAdminDto> result = productService.getAdminProducts(currentPage);
        model.addAttribute("products", result);
        return "/dashboard/product/index";
    }
}
