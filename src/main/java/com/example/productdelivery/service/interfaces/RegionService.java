package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.RegionDto;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.payload.ResponseApi;

import java.util.List;
import java.util.Optional;

public interface RegionService {

    List<Region> findAll();

    boolean existsByName(String name);

    Region findByName(String name);

    Region save(RegionDto regionDto);

    ResponseApi edit(Region region);

    ResponseApi deleteById(Long id);

    Optional<Region> findById(Long id);

    List<Region> findAllByCarrierName(String carrierName);
}
