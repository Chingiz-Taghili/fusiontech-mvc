package com.multishop.fusiontech.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceDto {
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

    private int paymentMethod;
}
