package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.brand.BrandCreateDto;
import com.multishop.fusiontech.dtos.brand.BrandDto;
import com.multishop.fusiontech.dtos.brand.BrandUpdateDto;
import com.multishop.fusiontech.models.Brand;
import com.multishop.fusiontech.repositories.BrandRepository;
import com.multishop.fusiontech.services.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BrandDto> getAllBrands() {
        List<Brand> repoBrands = brandRepository.findAll();
        List<BrandDto> brands = repoBrands.stream().map(brand -> modelMapper.map(brand, BrandDto.class)).toList();
        return brands;
    }

    @Override
    public Brand getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        return brand;
    }

    @Override
    public boolean createBrand(BrandCreateDto brandCreateDto) {
        try {
            Brand brand = modelMapper.map(brandCreateDto, Brand.class);
            brandRepository.save(brand);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateBrand(Long id, BrandUpdateDto brandUpdateDto) {
        try {
            Brand findBrand = brandRepository.findById(id).orElseThrow();
            findBrand.setName(brandUpdateDto.getName());
            brandRepository.save(findBrand);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
