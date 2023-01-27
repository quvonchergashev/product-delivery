package com.example.productdelivery;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
//@EnableConfigurationProperties({OpenApiProperties.class, ServiceProperties.class})
public class ProductDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductDeliveryApplication.class, args);
    }

}
