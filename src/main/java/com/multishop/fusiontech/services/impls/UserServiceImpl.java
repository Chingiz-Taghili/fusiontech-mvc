package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.auth.RegisterDto;
import com.multishop.fusiontech.dtos.product.ProductBasketDto;
import com.multishop.fusiontech.dtos.product.ProductShopDto;
import com.multishop.fusiontech.dtos.singledtos.UserCartDto;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.enums.Gender;
import com.multishop.fusiontech.models.Basket;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity findUser = userRepository.findByEmail(email);
        return findUser;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity repoUser = userRepository.findById(id).orElseThrow();
        UserDto user = modelMapper.map(repoUser, UserDto.class);
        return user;
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        UserEntity findUser = userRepository.findByEmail(registerDto.getEmail());
        if (findUser != null) {
            return false;
        }
        UserEntity newUser = modelMapper.map(registerDto, UserEntity.class);
        String password = encoder.encode(registerDto.getPassword());
        newUser.setPassword(password);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public boolean createUser(UserCreateDto userCreateDto) {
        UserEntity findUser = userRepository.findByEmail(userCreateDto.getEmail());
        if (findUser != null) {
            return false;
        }
        UserEntity newUser = modelMapper.map(userCreateDto, UserEntity.class);
        String password = encoder.encode(userCreateDto.getPassword());
        newUser.setPassword(password);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public boolean updateUser(Long id, UserUpdateDto userUpdateDto) {
        try {
            UserEntity findUser = userRepository.findById(id).orElseThrow();
            findUser.setName(userUpdateDto.getName());
            findUser.setSurname(userUpdateDto.getSurname());
            findUser.setEmail(userUpdateDto.getEmail());
            findUser.setGender(userUpdateDto.getGender());
            findUser.setImage(userUpdateDto.getImage());
            if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
                String password = encoder.encode(userUpdateDto.getPassword());
                findUser.setPassword(password);
            }
            userRepository.save(findUser);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> repoUsers = userRepository.findAll();
        List<UserDto> users = repoUsers.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return users;
    }

    @Override
    public UserCartDto getUserCart(String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        List<Basket> findUserBaskets = findUser.getBaskets();
        UserCartDto userCart = new UserCartDto();
        List<ProductBasketDto> productList = new ArrayList<>();
        for (Basket b : findUserBaskets) {
            ProductBasketDto pb = new ProductBasketDto();
            pb.setId(b.getProduct().getId());
            pb.setName(b.getProduct().getName());
            pb.setImage(b.getProduct().getImages().get(0).getUrl());
            pb.setPrice(b.getProduct().getPrice());
            pb.setDiscountPrice(b.getProduct().getDiscountPrice());
            pb.setQuantity(b.getQuantity());
            pb.setAmount((double) Math.round(b.getProduct().getDiscountPrice() * b.getQuantity() * 100) / 100);
            productList.add(pb);
        }
        double subtotal = (double) Math.round(productList.stream().mapToDouble(c -> c.getDiscountPrice() * c.getQuantity()).sum() * 100) / 100;
        double shipping = subtotal > 0 ? 19.99 : 0;
        double total = (double) Math.round((subtotal + shipping) * 100) / 100;
        userCart.setProducts(productList);
        userCart.setSubtotal(subtotal);
        userCart.setShipping(shipping);
        userCart.setTotal(total);
        return userCart;
    }

    @Override
    public int getUserCartSize(String userEmail) {
        if (userEmail == null) {
            return 0;
        }
        return getUserByEmail(userEmail).getBaskets().size();
    }

    @Override
    public List<ProductShopDto> getUserFavoriteProducts(String userEmail) {

        List<Product> repoProducts = userRepository.findByEmail(userEmail).getFavorites().stream().map(favorite -> favorite.getProduct()).toList();
        List<ProductShopDto> favoriteProducts = repoProducts.stream().map(product -> modelMapper.map(product, ProductShopDto.class)).toList();

        for (int i = 0; i < favoriteProducts.size(); i++) {
            favoriteProducts.get(i).setImage(repoProducts.get(i).getImages().get(0).getUrl());
        }

        return favoriteProducts;
    }

    @Override
    public int getUserFavoriteSize(String userEmail) {
        if (userEmail == null) {
            return 0;
        }
        return getUserByEmail(userEmail).getFavorites().size();
    }
}
