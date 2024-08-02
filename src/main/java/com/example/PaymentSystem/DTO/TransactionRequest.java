package com.example.PaymentSystem.DTO;

import com.example.PaymentSystem.Model.PaymentMethod;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.repository.query.parser.Part;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Payment method is mandatory")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Product is mandatory")
    private String product;

    @NotNull(message = "Amount is mandatory")
    private BigDecimal amount;

    @Valid
    private CardDetails cardDetails;

    public void validate() {
        if (paymentMethod== PaymentMethod.CASH) {
            cardDetails = null;
        } else if (paymentMethod == PaymentMethod.CARD && cardDetails == null) {
            throw new IllegalArgumentException("Card details are mandatory for card payment");
        }
    }

}
