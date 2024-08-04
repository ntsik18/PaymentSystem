package com.example.PaymentSystem.Service;


import com.example.PaymentSystem.DTO.TransactionRequest;
import com.example.PaymentSystem.DTO.TransactionResponse;
import com.example.PaymentSystem.Mapper.TransactionMapper;
import com.example.PaymentSystem.Model.Product;
import com.example.PaymentSystem.Model.Transaction;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.ProductRepository;
import com.example.PaymentSystem.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ProductRepository productRepository;

 /*
  The transaction will fail if the product does not exist in the db;
  In this case, the transaction will be saved in the db with the status FAILED.
  If cardDetails are not valid, or transaction amount is not within the product's amount range,
  a 400 response will be returned to the client with an error message and the transaction will not be saved in the db.
*/


    public TransactionResponse pay(TransactionRequest transactionRequest, Authentication authentication) {
        logger.info("Starting payment process for transaction request: {}", transactionRequest);
        if (transactionRequest.getPaymentMethod().name().equals("CARD")) {
            transactionRequest.validate(); //return error to client if card details are omitted
            validateCardExp(transactionRequest);  //validate exp date
        }
        var user = (User) authentication.getPrincipal();
        logger.info("Authenticated user: {}", user);
        Optional<Product> product = productRepository.findByDescriptionIgnoreCase(transactionRequest.getProduct());
        if (product.isEmpty()) {
            handleInvalidProduct(transactionRequest, user);
        }
        Product product1 = product.get();
        logger.info("Product found: " + product1.getDescription());

        validateAmount(product1, transactionRequest);
        logger.info("Transaction is in progress");
        Transaction transaction = transactionMapper.requestToTransaction(transactionRequest, user, product);
        logger.info("Transaction status set to Completed: {}", transaction);
        transactionRepository.save(transaction);
        logger.info("Transaction saved");
        TransactionResponse response = transactionMapper.transactionToResponse(transaction);
        response.setProduct(product1.getDescription());
        logger.info("Transaction response created: {}", response);
        return response;
    }

    private void validateAmount(Product product, TransactionRequest transactionRequest) {
        logger.info("Validating transaction amount for product");
        BigDecimal transactionAmount = transactionRequest.getAmount();
        BigDecimal minAmount = product.getMinAmount();
        BigDecimal maxAmount = product.getMaxAmount();
        if (transactionAmount.compareTo(minAmount) < 0) {
            logger.error("Amount validation has failed, minimum amount for transaction is " + minAmount);
            throw new IllegalArgumentException("Minimum amount for this transaction is " + minAmount);
        }
        if (transactionAmount.compareTo(maxAmount) > 0) {
            logger.error("Amount validation has failed, maximum amount for transaction is " + maxAmount);
            throw new IllegalArgumentException("Maximum amount for this transaction is " + maxAmount);
        } else logger.info("Transaction amount has been validated");


    }


    private void validateCardExp(TransactionRequest transactionRequest) {
        logger.info("Validating card credentials");
        boolean isCardValid = transactionRequest.getCardDetails().isValidExpirationDate();
        if (!isCardValid) {
            logger.error("Invalid card expiration date");
            throw new IllegalArgumentException("Invalid card expiration date");
        } else logger.info("Card has been validated");
    }

    public void handleInvalidProduct(TransactionRequest transactionRequest, User user) {
        logger.error("Product not found. Handling invalid product.");
        Transaction transaction = transactionMapper.requestToTransaction(transactionRequest, user, null);
        transactionRepository.save(transaction);
        logger.info("Transaction saved in handleInvalidProduct, transaction status set to Failed {}", transaction);
        throw new NullPointerException("Invalid product");
    }

    public List<TransactionResponse> showAllTransactions(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        transactions.forEach(transaction ->
                transactionResponses.add(transactionMapper.transactionToResponse(transaction))
        );
        return transactionResponses;

    }
}
