package com.test.interview.services;

import com.test.interview.data.PaymentResponse;
import com.test.interview.models.*;
import com.test.interview.repositories.PaymentRepository;
import com.test.interview.repositories.OrderDetailRepository;
import com.test.interview.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Payment processPayment(AppUser user, Long orderId, PaymentMethod paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Verificar que el usuario sea el dueño del pedido
        if (!order.getUser().equals(user)) {
            throw new RuntimeException("No tienes permiso para pagar este pedido.");
        }

        // Verificar que el pedido aún no tenga un pago registrado
        if (paymentRepository.findByOrder(order).isPresent()) {
            throw new RuntimeException("Este pedido ya ha sido pagado.");
        }

        // Obtener los detalles del pedido y calcular el total
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        double totalAmount = orderDetails.stream()
                .mapToDouble(detail -> detail.getPrice())
                .sum();

        // Crear el pago
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(user);
        payment.setAmount(totalAmount);  
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setPaymentDate(new Date());

        return paymentRepository.save(payment);
    }


    public List<PaymentResponse> getPaymentsByUser(AppUser user) {
        return paymentRepository.findByUser(user).stream().map(payment -> {
            PaymentResponse response = new PaymentResponse();
            response.setId(payment.getId());
            response.setAmount(payment.getAmount());
            response.setPaymentMethod(payment.getPaymentMethod());
            response.setStatus(payment.getStatus());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }).collect(Collectors.toList());
    }

    // 🔹 Reembolsar un pago (Solo ADMIN)
    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        // Cambiar el estado del pago a REJECTED (Simula un reembolso)
        payment.setStatus(PaymentStatus.REJECTED);
        paymentRepository.save(payment);
    }
}
