package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Requests,Long> {
}
