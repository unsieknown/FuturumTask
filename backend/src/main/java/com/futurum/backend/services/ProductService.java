package com.futurum.backend.services;

import com.futurum.backend.dto.ProductDto;
import com.futurum.backend.mappers.ProductMapper;
import com.futurum.backend.model.Product;
import com.futurum.backend.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public Optional<ProductDto> getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        product.setProductId(productId);

        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
