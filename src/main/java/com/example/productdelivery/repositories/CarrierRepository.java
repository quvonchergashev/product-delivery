package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier,Long> {
    boolean existsByName(String name);
    Carrier findByName(String name);
    List<Carrier> findCarriersByRegion_Name(String region_name);

    List<Carrier> findAllByRegion_Id(Long region_id);




}
