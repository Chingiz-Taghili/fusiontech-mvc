package com.multishop.fusiontech.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private Product product;
    private Long quantity;
}
