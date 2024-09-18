package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
