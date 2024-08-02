package com.example.PaymentSystem.Controller;


import com.example.PaymentSystem.DTO.TransactionRequest;
import com.example.PaymentSystem.DTO.TransactionResponse;
import com.example.PaymentSystem.Service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/payment")
    public ResponseEntity<TransactionResponse> makePayment(@Valid @RequestBody TransactionRequest transactionRequest, Authentication authentication) {
        return ResponseEntity.ok(transactionService.pay(transactionRequest, authentication));

    }

    @GetMapping("/myTransactions")
    public ResponseEntity<List<TransactionResponse>> showAllTransactions(Authentication authentication) {
        return ResponseEntity.ok(transactionService.showAllTransactions(authentication));
    }


}
