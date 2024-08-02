package com.example.PaymentSystem.Service;

import com.example.PaymentSystem.DTO.ProductDTO;
import com.example.PaymentSystem.Model.Product;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public String createProduct(ProductDTO productDto, Authentication authentication) {
        if (productRepository.findByDescriptionIgnoreCase(productDto.description()).isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        var user = (User) authentication.getPrincipal();
        Product product = Product.builder()
                .description(productDto.description())
                .minAmount(productDto.minAmount())
                .maxAmount(productDto.maxAmount())
                .user(user)
                .build();
        productRepository.save(product);
        return "Product has been created";
    }
}
