package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByNameContainingIgnoreCase(String keyword);
}
