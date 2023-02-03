package com.example.productdelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity

public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "carrier_region", joinColumns = { @JoinColumn(name = "carrier_id") }, inverseJoinColumns = {
            @JoinColumn(name = "region_id") })
    private List<Region> region = new LinkedList<>();
}
