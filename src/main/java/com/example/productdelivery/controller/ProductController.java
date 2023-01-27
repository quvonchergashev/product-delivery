package com.example.productdelivery.controller;

import com.example.productdelivery.dto.ProductDto;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize(value = "hasAnyAuthority('PRODUCT_READ')")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        ResponseApi all = productService.findAll();
        if (all.isSuccess()) return ResponseEntity.ok(all);
        return ResponseEntity.status(409).body(all);
    }

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(MultipartHttpServletRequest request, ProductDto productDto) throws IOException {
        ResponseApi responseApi = productService.add(productDto, request);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable Long productId){
        ResponseApi responseApi = productService.deleteById(productId);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

    @GetMapping("/get-user-products/{userId}")
    public ResponseEntity<?> getUserProducts(@PathVariable Long userId){
        ResponseApi userProducts = productService.getUserProducts(userId);
        if (userProducts.isSuccess()) return ResponseEntity.ok(userProducts);
        return ResponseEntity.status(409).body(userProducts);
    }








}
