package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.category.*;
import com.multishop.fusiontech.models.Category;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.repositories.CategoryRepository;
import com.multishop.fusiontech.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryLayoutDto> getLayoutCategories() {
        List<Category> repoCategories = categoryRepository.findAll();
        List<CategoryLayoutDto> layoutCategories = repoCategories.stream().map(category -> modelMapper.map(category, CategoryLayoutDto.class)).toList();
        return layoutCategories;
    }

    @Override
    public List<CategoryHomeDto> getHomeCategories() {
        List<Category> repoCategories = categoryRepository.findAll();
        List<CategoryHomeDto> homeCategories = repoCategories.stream().map(category -> modelMapper.map(category, CategoryHomeDto.class)).toList();
        for (int i = 0; i < homeCategories.size(); i++) {
            homeCategories.get(i).setProductQuantity(repoCategories.get(i).getProducts().size());
        }
        repoCategories.get(0).getProducts().size();
        return homeCategories;
    }

    @Override
    public List<CategoryShopDto> getShopCategories() {
        List<Category> repoCategories  = categoryRepository.findAll();
        List<CategoryShopDto> shopCategories = repoCategories.stream().map(category -> modelMapper.map(category, CategoryShopDto.class)).toList();
        return shopCategories;
    }

    @Override
    public List<CategoryAdminDto> getAdminCategories() {
        List<Category> findCategories = categoryRepository.findAll();
        List<CategoryAdminDto> adminCategories = findCategories.stream().map(category -> modelMapper.map(category, CategoryAdminDto.class)).toList();
        return adminCategories;
    }

    @Override
    public boolean createCategory(CategoryCreateDto categoryCreateDto) {
        try {
            Category category = modelMapper.map(categoryCreateDto, Category.class);
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        try {
            Category findCategory = categoryRepository.findById(id).orElseThrow();
            findCategory.setName(categoryUpdateDto.getName());
            findCategory.setImage(categoryUpdateDto.getImage());
            categoryRepository.save(findCategory);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        Category findCategory = categoryRepository.findById(id).orElseThrow();
        return findCategory;
    }
}
