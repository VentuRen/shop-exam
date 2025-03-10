package com.test.interview.repositories;

import com.test.interview.models.Payment;
import com.test.interview.models.Order;
import com.test.interview.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
    List<Payment> findByUser(AppUser user);
}
