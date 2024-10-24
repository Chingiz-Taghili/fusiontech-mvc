package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.singledtos.SubcategoryDto;
import com.multishop.fusiontech.models.Subcategory;

import java.util.List;

public interface SubcategoryService {

    List<SubcategoryDto> getAllSubcategories();

    Subcategory getById(Long id);
}
