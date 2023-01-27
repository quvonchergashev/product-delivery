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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carrierName;
    private Integer score=0;
    private Integer transactionNumber=0;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Offer offer;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Requests requests;

    @ManyToOne
    @JsonIgnore
    private User user;

}
