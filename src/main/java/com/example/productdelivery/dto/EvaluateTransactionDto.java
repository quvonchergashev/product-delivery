package com.example.productdelivery.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateTransactionDto {
    private Long transactionId;
    private Integer score;
}
