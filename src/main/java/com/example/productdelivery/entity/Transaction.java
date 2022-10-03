package com.example.productdelivery.entity;

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
    @OneToOne
    private Offer offer;
    @OneToOne
    private Requests requests;

}
