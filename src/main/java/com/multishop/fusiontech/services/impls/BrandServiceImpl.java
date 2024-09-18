package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.BrandDto;
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
}
