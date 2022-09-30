package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {
    boolean existsByName(String name);

    Place findByName(String name);
}
