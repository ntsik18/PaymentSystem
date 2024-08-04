package com.example.PaymentSystem.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails {

    @CreditCardNumber(message = "Not a valid Credit card")
    @NotBlank(message = "Card number is mandatory")
    private String cardNumber;

    @NotBlank(message = "Expiration date is mandatory")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Expiration date must be in MM/YY format")
    private String expDate;

    @NotBlank(message = "Security code is mandatory")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "Security code must be 3 or 4 digits")
    private String securityCode;


    public boolean isValidExpirationDate() {
        boolean cardIsValid = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth yearMonth = YearMonth.parse(expDate, formatter);
        YearMonth now = YearMonth.now();
        if (!(yearMonth.isAfter(now) || yearMonth.equals(now))) {
//                throw new IllegalArgumentException("Card has been expired");
            return cardIsValid;
        } else {
            cardIsValid = true;
        }
        return cardIsValid;
    }
}
