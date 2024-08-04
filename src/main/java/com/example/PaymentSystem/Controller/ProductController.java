package com.example.PaymentSystem.Controller;


import com.example.PaymentSystem.DTO.ProductDTO;
import com.example.PaymentSystem.DTO.ProductUpdateDTO;
import com.example.PaymentSystem.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDto, Authentication authentication) {
        return ResponseEntity.ok(productService.createProduct(productDto, authentication));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<ProductDTO> modifyProduct (@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productDTO, Authentication authentication) {
        return ResponseEntity.ok(productService.update (id, productDTO, authentication));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> addProduct(@PathVariable Long id, Authentication authentication) {
        productService.deleteProduct(id, authentication);
        return ResponseEntity.ok(String.format( "Product with id %d has been deleted ", id));
    }


}
