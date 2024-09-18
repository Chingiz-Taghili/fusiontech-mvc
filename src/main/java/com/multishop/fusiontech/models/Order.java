package com.multishop.fusiontech.models;

import com.multishop.fusiontech.enums.OrderStatus;
import com.multishop.fusiontech.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity user;
    private Date orderDate;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String city;
    private String address;
    private String message;

    private Boolean differBilling;
    private String billName;
    private String billSurname;
    private String billPhoneNumber;
    private String billEmail;
    private String billCity;
    private String billAddress;
    private String billMessage;

    private Integer paymentMethod;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
