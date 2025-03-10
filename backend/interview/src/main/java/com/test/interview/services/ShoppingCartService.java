package com.test.interview.services;

import com.test.interview.data.ShoppingCartResponse;
import com.test.interview.models.AppUser;
import com.test.interview.models.ShoppingCart;
import com.test.interview.models.Product;
import com.test.interview.repositories.ShoppingCartRepository;
import com.test.interview.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    
    public ShoppingCart addToCart(AppUser user, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return shoppingCartRepository.save(cartItem);
    }
    
    public void clearCart(AppUser user) {
        shoppingCartRepository.deleteByUser(user);
    }

    public List<ShoppingCartResponse> getCartByUser(AppUser user) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUser(user);

        return cartItems.stream().map(cart -> {
            ShoppingCartResponse response = new ShoppingCartResponse();
            response.setId(cart.getId());
            response.setProductName(cart.getProduct().getName());
            response.setQuantity(cart.getQuantity());
            response.setPrice(cart.getProduct().getPrice() * cart.getQuantity());
            return response;
        }).collect(Collectors.toList());
    }
    public void removeFromCart(Long cartItemId) {
        ShoppingCart cartItem = shoppingCartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Producto en carrito no encontrado"));
        shoppingCartRepository.delete(cartItem);
    }
    
    
}
