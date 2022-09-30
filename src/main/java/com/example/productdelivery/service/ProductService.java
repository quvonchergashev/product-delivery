package com.example.productdelivery.service;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        if (id == null) return null;
        return productRepository.findById(id).orElse(null);
    }
}
