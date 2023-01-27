package com.example.productdelivery.service;

import com.example.productdelivery.dto.ProductDto;
import com.example.productdelivery.entity.Attachment;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.AttachmentRepository;
import com.example.productdelivery.repositories.ProductRepository;
import com.example.productdelivery.service.interfaces.ProductService;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AttachmentRepository attachmentRepository;

    private final UserService userService;
    private static final String uploadDirectory = "productImages";
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    @Override
    public ResponseApi findAll() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId = userService.findById(principal.getId());
        List<Product> products=new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (!product.getUser().getId().equals(byId.get().getId())) {
                products.add(product);
            }
        }
        if (products.isEmpty()) return new ResponseApi("Product empty", false);
        return new ResponseApi("Success", true, products);
    }
    @Override
    public ResponseApi add(ProductDto productDto, MultipartHttpServletRequest request) throws IOException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findById(principal.getId());
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Product save = null;
        if (file != null) {
            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            String[] split = file.getOriginalFilename().split("\\.");
            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
            attachment.setName(name);
            attachmentRepository.save(attachment);

            Path path = Paths.get(uploadDirectory + "/" + name);
            Files.copy(file.getInputStream(), path);

            Product product1 = new Product();
            product1.setName(productDto.getName());
            product1.setImage(file.getOriginalFilename());
            product1.setPrice(productDto.getPrice());
            product1.setUser(user.get());
            save = productRepository.save(product1);
            return new ResponseApi("Success", true, save);
        }
        return new ResponseApi("Not found file",false);
    }
    @Override
    public ResponseApi deleteById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found product",false);
        productRepository.deleteById(id);
        return new ResponseApi("Success.Deleted product id="+id,true);
    }

    @Override
    public ResponseApi getUserProducts(Long id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found user id="+id,false);
        List<Product> allByUserId = productRepository.findAllByUserId(id);
        return new ResponseApi("Success",true,allByUserId);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

}
