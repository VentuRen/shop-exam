package com.test.interview.controllers;

import com.test.interview.data.OrderResponse;
import com.test.interview.models.AppUser;
import com.test.interview.models.Order;
import com.test.interview.security.JwtUtil;
import com.test.interview.services.OrderService;
import com.test.interview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    
    @PostMapping
     public ResponseEntity<OrderResponse> confirmOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderRequest request) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = orderService.createOrderFromCart(user, request.getShippingAddress());
        return ResponseEntity.ok(orderService.convertToOrderResponse(order));
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = orderService.getOrderById(id);
        if (!order.getUser().equals(user)) {
            return ResponseEntity.status(403).body(null); 
        }

        return ResponseEntity.ok(orderService.convertToOrderResponse(order));
    }


    static class OrderRequest {
        private String shippingAddress;
        public String getShippingAddress() { return shippingAddress; }
    }
}
