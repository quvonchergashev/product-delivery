package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.PlaceDto;
import com.example.productdelivery.dto.PlaceEditDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.payload.ResponseApi;

import java.util.List;
import java.util.TreeSet;

public interface PlaceService {

    Place save(Place place);
    List<Place> findAll();
    List<PlaceDto> notExistPlaces(List<PlaceDto> places);
    TreeSet<Place> saveAll(List<PlaceDto> placeDtos, Region region);
    Place findByName(String name);
    boolean existsByName(String name);

    ResponseApi edit(PlaceEditDto placeEditDto);
    ResponseApi deleteFindById(Long id);
}
