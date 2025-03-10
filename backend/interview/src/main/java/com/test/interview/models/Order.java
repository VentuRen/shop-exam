package com.test.interview.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetail> details;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}

