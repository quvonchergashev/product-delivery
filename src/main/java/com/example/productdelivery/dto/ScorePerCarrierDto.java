package com.example.productdelivery.dto;

import com.example.productdelivery.entity.Carrier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorePerCarrierDto {
    private Integer score;
    private Carrier carrier;
}
