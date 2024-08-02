package com.example.PaymentSystem.DTO;

import com.example.PaymentSystem.Model.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    @NotBlank(message = "Firstname is mandatory")
    private String lastName;
    @Email(message = "Email is not well formatted")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
    @NotNull(message = "Role is mandatory")
    private Role role;

}
