package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.BasketAddDto;
import com.multishop.fusiontech.models.Basket;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.BasketRepository;
import com.multishop.fusiontech.repositories.ProductRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.BasketService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public BasketServiceImpl(BasketRepository basketRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addToCart(BasketAddDto basketAddDto, String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        Product findProduct = productRepository.findById(basketAddDto.getProductId()).orElseThrow();
        Basket findBasket = basketRepository.findByUserIdAndProductId(findUser.getId(), findProduct.getId());

        if (findBasket == null) {
            Basket basket = new Basket();
            basket.setUser(findUser);
            basket.setProduct(findProduct);
            basket.setQuantity(basketAddDto.getQuantity());
            basketRepository.save(basket);
        } else {
            findBasket.setQuantity(findBasket.getQuantity() + basketAddDto.getQuantity());
            basketRepository.save(findBasket);
        }
        return true;
    }

    @Override
    public boolean removeBasket(Long productId, String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        Basket findBasket = basketRepository.findByUserIdAndProductId(findUser.getId(), productId);
        basketRepository.delete(findBasket);
        return true;
    }
}
