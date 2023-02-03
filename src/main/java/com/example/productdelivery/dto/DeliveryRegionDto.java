package com.example.productdelivery.dto;

import com.example.productdelivery.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRegionDto {

    private Integer transactionNumber;
    private List<Region> regionList;
}
