package com.example.PaymentSystem.Repository;

import com.example.PaymentSystem.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByDescriptionIgnoreCase(String description);
}
