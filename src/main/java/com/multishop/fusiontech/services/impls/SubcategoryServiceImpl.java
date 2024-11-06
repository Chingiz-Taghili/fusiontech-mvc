package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.SubcategoryDto;
import com.multishop.fusiontech.models.Subcategory;
import com.multishop.fusiontech.repositories.SubcategoryRepository;
import com.multishop.fusiontech.services.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SubcategoryDto> getAllSubcategories() {
        List<Subcategory> repoSubcategories = subcategoryRepository.findAll();
        List<SubcategoryDto> subcategories = repoSubcategories.stream().map(subcategory -> modelMapper.map(subcategory, SubcategoryDto.class)).toList();
        return subcategories;
    }

    @Override
    public SubcategoryDto getSubcategoryById(Long id) {
        Subcategory repoSubcategory = subcategoryRepository.findById(id).orElseThrow();
        SubcategoryDto subcategory = modelMapper.map(repoSubcategory, SubcategoryDto.class);
        return subcategory;
    }
}
