package com.test.interview.controllers;

import com.test.interview.data.PaymentResponse;
import com.test.interview.models.*;
import com.test.interview.security.JwtUtil;
import com.test.interview.services.PaymentService;
import com.test.interview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔹 Realizar un pago
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @RequestHeader("Authorization") String token,
            @RequestBody PaymentRequest request) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Payment payment = paymentService.processPayment(user, request.getOrderId(), request.getPaymentMethod());
        return ResponseEntity.ok(paymentService.getPaymentsByUser(user).get(0));
    }

   
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getUserPayments(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(paymentService.getPaymentsByUser(user));
    }

    
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> refundPayment(@PathVariable Long id) {
        paymentService.refundPayment(id);
        return ResponseEntity.ok("Pago reembolsado exitosamente.");
    }

    // Clase interna para recibir datos de la solicitud
    static class PaymentRequest {
        private Long orderId;
        private PaymentMethod paymentMethod;

        public Long getOrderId() { return orderId; }
        public PaymentMethod getPaymentMethod() { return paymentMethod; }
    }
}
