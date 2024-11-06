package com.multishop.fusiontech.dtos.user;

import com.multishop.fusiontech.enums.Gender;
import com.multishop.fusiontech.models.CartItem;
import com.multishop.fusiontech.models.Favorite;
import com.multishop.fusiontech.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String image;
    private String email;
    private String password;
    private Gender gender;
    private List<Favorite> favorites;
    private List<CartItem> cartItems;
    private List<Order> orders;
}
