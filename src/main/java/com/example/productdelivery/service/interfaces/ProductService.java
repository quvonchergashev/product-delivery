package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.ProductDto;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.payload.ResponseApi;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);
    ResponseApi findAll();
    ResponseApi add(ProductDto productDto, MultipartHttpServletRequest request) throws IOException;
    ResponseApi deleteById(Long id);
    ResponseApi getUserProducts(Long id);
    Product save(Product product);


}
