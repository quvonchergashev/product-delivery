package com.example.productdelivery.repositories;
import com.example.productdelivery.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
    boolean existsByName(String name);
    Region findByName(String soato);
}
