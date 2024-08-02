package com.example.PaymentSystem.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String product;
    private String paymentMethod;
    private String amount;
    private String transactionStatus;
    private String localDateTime;



}
