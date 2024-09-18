package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findAllByOrderByNumberAsc();
}
