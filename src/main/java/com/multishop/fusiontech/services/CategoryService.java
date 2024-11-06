package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.category.*;
import com.multishop.fusiontech.models.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getSearchCategories(String keyword);

    List<CategoryDto> getAllCategories();

    boolean createCategory(CategoryCreateDto categoryCreateDto);

    boolean updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);
}
