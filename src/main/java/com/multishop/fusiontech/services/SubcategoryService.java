package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.common.SubcategoryDto;

import java.util.List;

public interface SubcategoryService {

    List<SubcategoryDto> getAllSubcategories();

    SubcategoryDto getSubcategoryById(Long id);
}
