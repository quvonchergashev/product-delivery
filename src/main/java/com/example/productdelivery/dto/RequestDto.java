package com.example.productdelivery.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String placeName;
    private Long productId;
}
