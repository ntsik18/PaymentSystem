package com.example.PaymentSystem.DTO;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;


public record ProductUpdateDTO(

        String description,
        @PositiveOrZero
        BigDecimal minAmount,
        @PositiveOrZero
        BigDecimal maxAmount
) {
    public ProductUpdateDTO {
        if(minAmount!=null && maxAmount!=null)
            if (minAmount.compareTo(maxAmount) > 0) {
                System.out.println("in Dto constructor");
                throw new IllegalArgumentException("minAmount must be less than or equal to maxAmount");
            }
    }




}
