package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.brand.BrandCreateDto;
import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.brand.BrandUpdateDto;
import com.multishop.fusiontech.models.Brand;

import java.util.List;

public interface BrandService {

    List<BrandDto> getAllBrands();

    Brand getBrandById(Long id);

    boolean createBrand(BrandCreateDto brandCreateDto);

    boolean updateBrand(Long id, BrandUpdateDto brandUpdateDto);

    void deleteBrand(Long id);
}
