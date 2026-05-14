package com.futurum.backend.mappers;

import com.futurum.backend.dto.ProductDto;
import com.futurum.backend.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
