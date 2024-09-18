package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.auth.RegisterDto;
import com.multishop.fusiontech.dtos.product.ProductShopDto;
import com.multishop.fusiontech.dtos.singledtos.UserCartDto;
import com.multishop.fusiontech.models.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUserByEmail(String email);

    boolean register(RegisterDto registerDto);

    UserCartDto getUserCart(String userEmail);

    int getUserCartSize(String userEmail);

    List<ProductShopDto> getUserFavoriteProducts(String userEmail);

    int getUserFavoriteSize(String userEmail);

}
