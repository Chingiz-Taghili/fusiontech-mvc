package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.cart.CartItemCreateDto;
import com.multishop.fusiontech.models.CartItem;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.CartItemRepository;
import com.multishop.fusiontech.repositories.ProductRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addToCart(CartItemCreateDto cartItemCreateDto, String userEmail) {
        try {
            UserEntity findUser = userRepository.findByEmail(userEmail);
            Product findProduct = productRepository.findById(cartItemCreateDto.getProductId()).orElseThrow();
            CartItem findItem = cartItemRepository.findByUserIdAndProductId(findUser.getId(), findProduct.getId());

            if (findItem == null) {
                CartItem item = new CartItem();
                item.setUser(findUser);
                item.setProduct(findProduct);
                item.setQuantity(cartItemCreateDto.getQuantity());
                cartItemRepository.save(item);
            } else {
                findItem.setQuantity(findItem.getQuantity() + cartItemCreateDto.getQuantity());
                cartItemRepository.save(findItem);
            }

            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public void deleteCartItem(Long productId, String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        CartItem findItem = cartItemRepository.findByUserIdAndProductId(findUser.getId(), productId);
        cartItemRepository.delete(findItem);
    }
}
