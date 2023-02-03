package com.example.productdelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Place> places = new HashSet<>();

    @ManyToMany(mappedBy = "region")
    @JsonIgnore
    List<Carrier> carriers;

    public void removeCarrier(Carrier carrier){
        this.getCarriers().remove(carrier);
        carrier.getRegion().remove(this);
    }
    public void removeCarriers(){
        for (Carrier carrier : new ArrayList<>(carriers)) {
            removeCarrier(carrier);
        }
    }

}
