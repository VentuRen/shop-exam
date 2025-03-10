package com.test.interview.repositories;

import com.test.interview.models.Order;
import com.test.interview.models.AppUser;
import com.test.interview.models.OrderStatus;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(AppUser user);
    List<Order> findByStatus(OrderStatus status);

    
    @EntityGraph(attributePaths = {"details", "details.product"})
    Optional<Order> findById(Long id);
}
