package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.cart.CartDto;
import com.multishop.fusiontech.dtos.cart.CartItemDto;
import com.multishop.fusiontech.dtos.product.ProductDto;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.models.CartItem;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<UserDto> getSearchUsers(String keyword) {
        List<UserEntity> repoUsers = userRepository.findByKeywordInColumnsIgnoreCase(keyword);
        List<UserDto> users = repoUsers.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return users;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity findUser = userRepository.findByEmail(email);
        UserDto user = modelMapper.map(findUser, UserDto.class);
        return user;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity repoUser = userRepository.findById(id).orElseThrow();
        UserDto user = modelMapper.map(repoUser, UserDto.class);
        return user;
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
    public PaginationPayload<UserDto> getAllUsers(Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        Page<UserEntity> repoUsers = userRepository.findAll(pageable);

        List<UserDto> users = repoUsers.getContent().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        PaginationPayload<UserDto> paginationUsers = new PaginationPayload<>(repoUsers.getTotalPages(), pageNumber, users);
        return paginationUsers;
    }

    @Override
    public CartDto getUserCart(String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        List<CartItem> findUserItems = findUser.getCartItems();
        CartDto userCart = new CartDto();
        List<CartItemDto> productList = new ArrayList<>();
        for (CartItem item : findUserItems) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getProduct().getId());
            itemDto.setName(item.getProduct().getName());
            itemDto.setImage(item.getProduct().getImages().get(0).getUrl());
            itemDto.setPrice(item.getProduct().getPrice());
            itemDto.setDiscountPrice(item.getProduct().getDiscountPrice());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setAmount((double) Math.round(item.getProduct().getDiscountPrice() * item.getQuantity() * 100) / 100);
            productList.add(itemDto);
        }
        double subtotal = (double) Math.round(productList.stream().mapToDouble(c -> c.getDiscountPrice() * c.getQuantity()).sum() * 100) / 100;
        double shipping = subtotal > 0 ? 19.99 : 0;
        double total = (double) Math.round((subtotal + shipping) * 100) / 100;
        userCart.setCartItems(productList);
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
        return getUserByEmail(userEmail).getCartItems().size();
    }

    @Override
    public PaginationPayload<ProductDto> getUserFavoriteProducts(String userEmail, Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));

        List<Product> repoProducts = userRepository.findByEmail(userEmail)
                .getFavorites().stream().map(favorite -> favorite.getProduct()).toList();

        int totalItems = repoProducts.size();
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min(start + pageable.getPageSize(), totalItems);

        List<ProductDto> favoriteProducts = repoProducts.subList(start, end)
                .stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        for (int i = 0; i < favoriteProducts.size(); i++) {
            favoriteProducts.get(i).setImage(repoProducts.get(start + i).getImages().get(0).getUrl());
        }

        int totalPages = (int) Math.ceil((double) totalItems / pageable.getPageSize());

        PaginationPayload<ProductDto> paginationProducts =
                new PaginationPayload<>(totalPages, pageNumber, favoriteProducts);

        return paginationProducts;
    }

    @Override
    public int getUserFavoriteSize(String userEmail) {
        if (userEmail == null) {
            return 0;
        }
        return getUserByEmail(userEmail).getFavorites().size();
    }
}
