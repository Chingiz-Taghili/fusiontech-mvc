package com.multishop.fusiontech.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    @OneToMany(mappedBy = "category")
    private List<Subcategory> subcategories;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
