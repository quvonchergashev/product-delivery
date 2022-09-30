package com.example.productdelivery.dto;

import com.example.productdelivery.entity.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PlaceDto {
    private Long id;

    private String name;

    public PlaceDto(Place place) {
        this.id = place.getId();
        this.name = place.getName();
    }
}
