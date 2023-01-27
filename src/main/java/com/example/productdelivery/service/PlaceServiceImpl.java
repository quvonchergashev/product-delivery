package com.example.productdelivery.service;

import com.example.productdelivery.dto.PlaceDto;
import com.example.productdelivery.dto.PlaceEditDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.PlaceRepository;
import com.example.productdelivery.service.interfaces.PlaceService;
import com.example.productdelivery.service.interfaces.RegionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Service
@Transactional
//@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    private final RegionService regionService;

    public PlaceServiceImpl(PlaceRepository placeRepository, @Lazy RegionService regionService) {
        this.placeRepository = placeRepository;
        this.regionService = regionService;
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }
    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }
    @Override
    public List<PlaceDto> notExistPlaces(List<PlaceDto> places) {
        return places.stream()
                .filter((place) -> !placeRepository.existsByName(place.getName()))
                .toList();
    }
    @Override
    public TreeSet<Place> saveAll(List<PlaceDto> placeDtos, Region region) {
        var placeList = placeDtos.stream()
                .map(placeDto -> new Place(placeDto, region))
                .toList();
        return new TreeSet<>(placeRepository.saveAll(placeList));
    }
    @Override
    public Place findByName(String name) {
        return placeRepository.findByName(name);
    }
    @Override
    public boolean existsByName(String name) {
        return placeRepository.existsByName(name);
    }

    @Override
    public ResponseApi edit(PlaceEditDto placeEditDto) {
        Optional<Place> byId = placeRepository.findById(placeEditDto.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found place",false);
        Optional<Region> byId1 = regionService.findById(placeEditDto.getRegionId());
        if (byId1.isEmpty()) return new ResponseApi("Not found region",false);
        Place place = byId.get();
        place.setName(placeEditDto.getName());
        place.setRegion(byId1.get());
        Place save = placeRepository.save(place);
        return new ResponseApi("Succes",true,save);
    }
    @Override
    public ResponseApi deleteFindById(Long id) {
        Optional<Place> byId = placeRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found place id="+id,false);
        placeRepository.deleteById(id);
        return new ResponseApi("Succes",true,"id"+id);
    }
}
