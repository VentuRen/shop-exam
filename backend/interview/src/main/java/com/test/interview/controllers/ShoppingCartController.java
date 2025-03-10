package com.test.interview.controllers;

import com.test.interview.data.ShoppingCartResponse;
import com.test.interview.models.AppUser;
import com.test.interview.models.ShoppingCart;
import com.test.interview.security.JwtUtil;
import com.test.interview.services.ShoppingCartService;
import com.test.interview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔹 Agregar producto al carrito
    @PostMapping
    public ResponseEntity<ShoppingCartResponse> addToCart(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequest request) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cartItem = shoppingCartService.addToCart(user, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(shoppingCartService.getCartByUser(user).get(0));
    }

    
    @GetMapping
    public ResponseEntity<List<ShoppingCartResponse>> getCart(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(shoppingCartService.getCartByUser(user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        shoppingCartService.removeFromCart(id);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }


    
    static class CartRequest {
        private Long productId;
        private Integer quantity;

        public Long getProductId() { return productId; }
        public Integer getQuantity() { return quantity; }
    }
}
