package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Offer;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByUserId(Long user_id);

}
