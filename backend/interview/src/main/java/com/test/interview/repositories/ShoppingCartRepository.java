package com.test.interview.repositories;

import com.test.interview.models.ShoppingCart;

import jakarta.transaction.Transactional;

import com.test.interview.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUser(AppUser user);
    @Transactional
    void deleteByUser(AppUser user); // Vaciar carrito después de la compra
}