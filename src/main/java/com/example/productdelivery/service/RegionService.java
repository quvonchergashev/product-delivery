package com.example.productdelivery.service;
import com.example.productdelivery.dto.RegionDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final PlaceService placeService;

    public List<Region> findAll(){
        return regionRepository.findAll();
    }
    public boolean existsByName(String name){
        return regionRepository.existsByName(name);
    }
    public Region findByName(String name){return regionRepository.findByName(name);}

    @Transactional
    public Region save(RegionDto regionDto) {
        var name = regionDto.getName();
        var placeList = regionDto.getPlaces();

        var region = new Region();
        region.setName(name);

        if (regionRepository.existsByName(name)) {
            region = regionRepository.findByName(name);
        }
        Region finalRegion = region;
        List<Place> placeList1 = placeList.stream()
                .filter(dto -> !placeService.existsByName(dto.getName()))
                .map(placeDto -> new Place(placeDto, finalRegion))
                .toList();

        finalRegion.getPlaces().addAll(placeList1);
        return regionRepository.save(region);
    }



}
