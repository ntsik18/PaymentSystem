package com.example.PaymentSystem.Mapper;


import com.example.PaymentSystem.DTO.TransactionRequest;
import com.example.PaymentSystem.DTO.TransactionResponse;
import com.example.PaymentSystem.Model.Product;
import com.example.PaymentSystem.Model.Transaction;
import com.example.PaymentSystem.Model.TransactionStatus;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final TransactionRepository transactionRepository;

    public TransactionResponse transactionToResponse(Transaction transaction) {
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionStatus(transaction.getStatus().name())
                .amount(transaction.getAmount().toString())
                .paymentMethod(transaction.getPaymentMethod().name())
                .localDateTime(transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        try {
            transactionResponse.setProduct(transaction.getProduct().getDescription());
        } catch (NullPointerException e) {
            transactionResponse.setProduct("Invalid product");
        }
        return transactionResponse;
    }

    public Transaction requestToTransaction(TransactionRequest request, User user, Optional<Product> product) {

        Transaction transaction = Transaction.builder()
                .user(user)
                .transactionDate(LocalDateTime.now())
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .build();
        try {
            transaction.setProduct(product.get());
            transaction.setStatus(TransactionStatus.Completed);
        } catch (NullPointerException e) {
            transaction.setProduct(null);
            transaction.setStatus(TransactionStatus.Failed);
        }
        return transaction;

    }
}
