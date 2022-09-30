package com.example.productdelivery.service;

import com.example.productdelivery.dto.PlaceDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.repositories.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeSet;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Place save(Place place) {
        return placeRepository.save(place);
    }



    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public List<PlaceDto> notExistPlaces(List<PlaceDto> places) {
        return places.stream()
                .filter((place) -> !placeRepository.existsByName(place.getName()))
                .toList();
    }

    public TreeSet<Place> saveAll(List<PlaceDto> placeDtos,Region region) {
        var placeList = placeDtos.stream()
                .map(placeDto -> new Place(placeDto,region))
                .toList();
        return new TreeSet<>(placeRepository.saveAll(placeList));
    }

    public Place findByName(String name){
        return placeRepository.findByName(name);
    }

    public boolean existsByName(String name) {
        return placeRepository.existsByName(name);
    }
}
