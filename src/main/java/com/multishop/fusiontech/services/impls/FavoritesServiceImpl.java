package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.models.Favorite;
import com.multishop.fusiontech.models.Product;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.FavoritesRepository;
import com.multishop.fusiontech.repositories.ProductRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.FavoritesService;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoritesRepository favoritesRepository;

    public FavoritesServiceImpl(UserRepository userRepository, ProductRepository productRepository, FavoritesRepository favoritesRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public boolean addToFavorites(Long productId, String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        Product findProduct = productRepository.findById(productId).orElseThrow();
        Favorite findFavorite = favoritesRepository.findByUserIdAndProductId(findUser.getId(), findProduct.getId());

        if (findFavorite == null) {
            Favorite favorite = new Favorite();
            favorite.setUser(findUser);
            favorite.setProduct(findProduct);
            favoritesRepository.save(favorite);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFavorite(Long productId, String userEmail) {
        UserEntity findUser = userRepository.findByEmail(userEmail);
        Product findProduct = productRepository.findById(productId).orElseThrow();
        Favorite findFavorite = favoritesRepository.findByUserIdAndProductId(findUser.getId(), findProduct.getId());
        favoritesRepository.delete(findFavorite);
        return true;
    }
}
