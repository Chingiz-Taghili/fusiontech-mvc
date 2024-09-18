package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.category.*;
import com.multishop.fusiontech.models.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryLayoutDto> getLayoutCategories();

    List<CategoryHomeDto> getHomeCategories();

    List<CategoryShopDto> getShopCategories();

    List<CategoryAdminDto> getAdminCategories();

    boolean createCategory(CategoryCreateDto categoryCreateDto);

    boolean updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    Category getCategoryById(Long id);
}
