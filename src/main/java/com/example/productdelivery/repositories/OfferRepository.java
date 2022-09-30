package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferRepository extends JpaRepository<Offer,Long> {
}
