package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findAllByUserId(Long user_id);
}
