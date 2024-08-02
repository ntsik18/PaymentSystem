package com.example.PaymentSystem.Model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String description;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
