package com.example.PaymentSystem.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



public record ProductDTO(
        @Valid
        @NotBlank(message = "Description is mandatory")
        String description,
        @NotNull
        @PositiveOrZero
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal minAmount,
        @NotNull
        @PositiveOrZero
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal maxAmount
) {
    public ProductDTO {
        if (minAmount.compareTo(maxAmount) > 0) {
            System.out.println("in Dto constructor");
            throw new IllegalArgumentException("minAmount must be less than or equal to maxAmount");
        }
    }

}
