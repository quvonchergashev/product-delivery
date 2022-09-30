package com.example.productdelivery.entity;

import com.example.productdelivery.dto.PlaceDto;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_region")
    @ToString.Exclude
    private Region region;

    public Place(PlaceDto placeDto, final Region region) {
        this.id = placeDto.getId();
        this.name = placeDto.getName();
        this.region = region;
    }
}
