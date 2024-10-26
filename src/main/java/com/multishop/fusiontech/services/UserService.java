package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.auth.RegisterDto;
import com.multishop.fusiontech.dtos.product.ProductShopDto;
import com.multishop.fusiontech.dtos.singledtos.UserCartDto;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.models.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUserByEmail(String email);

    UserDto getUserById(Long id);

    boolean register(RegisterDto registerDto);

    boolean createUser(UserCreateDto userCreateDto);

    boolean updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();

    UserCartDto getUserCart(String userEmail);

    int getUserCartSize(String userEmail);

    List<ProductShopDto> getUserFavoriteProducts(String userEmail);

    int getUserFavoriteSize(String userEmail);

}
