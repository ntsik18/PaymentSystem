package com.example.PaymentSystem.Service;

import com.example.PaymentSystem.DTO.ProductDTO;
import com.example.PaymentSystem.DTO.ProductUpdateDTO;
import com.example.PaymentSystem.Model.Product;
import com.example.PaymentSystem.Model.User;
import com.example.PaymentSystem.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
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


    public void deleteProduct(Long id, Authentication authentication) {
        //admin can delete any product, even the product created by another admin
//        User user =authentication.getPrincipal();
        productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
        productRepository.deleteById(id);
    }


    public ProductDTO update(Long id, ProductUpdateDTO productDTO, Authentication authentication) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        User user = (User) authentication.getPrincipal();
        updateProductFields(product, productDTO);
        product.setUser(user);
        productRepository.save(product);
        return new ProductDTO(product.getDescription(), product.getMinAmount(), product.getMaxAmount());
    }

    private void updateProductFields(Product product, ProductUpdateDTO productDTO) {
        if (productDTO.description() != null) {
            product.setDescription(productDTO.description());
        }
        if (productDTO.minAmount() != null) {
            if (productDTO.minAmount().compareTo(product.getMaxAmount())<1 ) {
                product.setMinAmount(productDTO.minAmount());
            } else throw new IllegalArgumentException("Minimum amount cannot exceed maximum amount");
        }
        if (productDTO.maxAmount() != null) {
            if (productDTO.maxAmount().compareTo(product.getMinAmount())>-1) {
                product.setMaxAmount(productDTO.maxAmount());
            } else throw new IllegalArgumentException("Maximum amount cannot be less than minimum amount");
        }
    }
}
