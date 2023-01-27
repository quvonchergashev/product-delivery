package com.example.productdelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placeName;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JsonIgnore
    private User user;
}
