package com.example.productdelivery.entity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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
    @OneToMany(mappedBy = "region",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Place> places =new HashSet<>();

}
