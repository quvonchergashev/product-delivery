package com.example.productdelivery.dto;

import com.example.productdelivery.entity.Place;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceEditDto {
    private Long id;
    private String name;
    private Long regionId;
}
