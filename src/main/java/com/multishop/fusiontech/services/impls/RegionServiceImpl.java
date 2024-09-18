package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.models.Region;
import com.multishop.fusiontech.repositories.RegionRepository;
import com.multishop.fusiontech.services.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> getRegions() {
        List<Region> regions = regionRepository.findAllByOrderByNumberAsc();
        return regions;
    }
}
