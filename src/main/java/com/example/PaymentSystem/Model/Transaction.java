package com.example.PaymentSystem.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", transactionDate=" + transactionDate +
//                ", product=" + product.getDescription() +
                '}';
    }
}
