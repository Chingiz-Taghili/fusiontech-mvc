package com.multishop.fusiontech.dtos.order;

import com.multishop.fusiontech.enums.OrderStatus;
import com.multishop.fusiontech.enums.PaymentMethod;
import com.multishop.fusiontech.enums.PaymentStatus;
import com.multishop.fusiontech.models.OrderItem;
import com.multishop.fusiontech.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private UserEntity user;
    private LocalDateTime orderDate;
    private String formattedOrderDate;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String city;
    private String address;
    private String message;

    private boolean differBilling;
    private String billName;
    private String billSurname;
    private String billPhoneNumber;
    private String billEmail;
    private String billCity;
    private String billAddress;
    private String billMessage;

    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private List<OrderItem> orderItems;
}
