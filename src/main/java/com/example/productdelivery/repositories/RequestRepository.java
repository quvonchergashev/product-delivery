package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Requests,Long> {

    List<Requests> findAllByUserId(Long user_id);
}
