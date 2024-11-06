package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.cart.CartDto;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.payloads.PaginationPayload;

import java.util.List;

public interface UserService {

    List<UserDto> getSearchUsers(String keyword);

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long id);

    boolean createUser(UserCreateDto userCreateDto);

    boolean updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

    PaginationPayload<UserDto> getAllUsers(Integer pageNumber);

    CartDto getUserCart(String userEmail);

    int getUserCartSize(String userEmail);

    PaginationPayload<ProductDto> getUserFavoriteProducts(String userEmail, Integer pageNumber);

    int getUserFavoriteSize(String userEmail);

}
