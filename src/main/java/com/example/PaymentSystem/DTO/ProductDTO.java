package com.example.PaymentSystem.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;



public record ProductDTO(
        @Valid
        @NotBlank(message = "Description is mandatory")
        String description,
        @NotNull
        @PositiveOrZero
        BigDecimal minAmount,
        @NotNull
        @PositiveOrZero
        BigDecimal maxAmount
) {
    public ProductDTO {
        if (minAmount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException("minAmount must be less than or equal to maxAmount");
        }
    }

}
