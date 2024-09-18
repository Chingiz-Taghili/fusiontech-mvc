package com.multishop.fusiontech.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String moreDetail;
    private Double discountPrice;
    private Date discountDate;
    private Boolean featured;
    private Boolean offered;
    private Double rating;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Subcategory subcategory;
}
