package com.test.interview.services;

import com.test.interview.data.OrderDetailResponse;
import com.test.interview.data.OrderResponse;
import com.test.interview.models.*;
import com.test.interview.repositories.OrderRepository;
import com.test.interview.repositories.OrderDetailRepository;
import com.test.interview.repositories.ShoppingCartRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Transactional
    public Order createOrderFromCart(AppUser user, String shippingAddress) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        // Crear el pedido
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        // Crear detalles del pedido
        for (ShoppingCart item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice() * item.getQuantity());
            

            orderDetailRepository.save(detail);
        }
        // Vaciar el carrito después de confirmar el pedido
        shoppingCartRepository.deleteByUser(user);

        return order;
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

     public OrderResponse convertToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setShippingAddress(order.getShippingAddress());
        response.setStatus(order.getStatus());

        // Convertir los detalles del pedido a DTOs
        List<OrderDetailResponse> details = orderDetailRepository.findByOrder(order).stream().map(detail -> {
            OrderDetailResponse detailResponse = new OrderDetailResponse();
            detailResponse.setProductName(detail.getProduct().getName());
            detailResponse.setQuantity(detail.getQuantity());
            detailResponse.setPrice(detail.getPrice());
            return detailResponse;
        }).collect(Collectors.toList());

        response.setDetails(details);
        return response;
    }
}
