package com.example.productdelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @Size(min = 3)
    private String name;

    @Column(nullable = false)
    @Min(message = "a negative number cannot occur",value = 0)
    private Double price;

    private String image;


    @ManyToOne
    @JsonIgnore
    private User user;

}
