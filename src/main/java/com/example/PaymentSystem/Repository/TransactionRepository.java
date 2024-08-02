package com.example.PaymentSystem.Repository;

import com.example.PaymentSystem.Model.Transaction;
import com.example.PaymentSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);
}
