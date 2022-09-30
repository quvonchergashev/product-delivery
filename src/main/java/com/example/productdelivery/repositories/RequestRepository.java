package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RequestRepository extends JpaRepository<Requests,Long> {
}
