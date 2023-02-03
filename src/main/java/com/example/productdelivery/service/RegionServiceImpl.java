package com.example.productdelivery.service;

import com.example.productdelivery.dto.RegionDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.RegionRepository;
import com.example.productdelivery.service.interfaces.CarrierService;
import com.example.productdelivery.service.interfaces.PlaceService;
import com.example.productdelivery.service.interfaces.RegionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service

public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final PlaceService placeService;
    private final CarrierService carrierService;

    public RegionServiceImpl(RegionRepository regionRepository, PlaceService placeService, @Lazy CarrierService carrierService) {
        this.regionRepository = regionRepository;
        this.placeService = placeService;
        this.carrierService = carrierService;
    }

    @Override
    public List<Region> findAll() {
        return  regionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Region::getName))
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return regionRepository.existsByName(name);
    }

    @Override
    public Region findByName(String name) {
        return regionRepository.findByName(name);
    }

    @Override
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
    @Override
    public ResponseApi edit(Region region) {
        Optional<Region> byId = regionRepository.findById(region.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found region",false);
        Region region1 = byId.get();
        region1.setName(region.getName());
        region1.setPlaces(region.getPlaces());
        Region save = regionRepository.save(region1);
        return new ResponseApi("Success edited",true,save);
    }
    @Override
    public ResponseApi deleteById(Long id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found region",false);
        byId.get().removeCarriers();
        regionRepository.deleteById(id);
        return new ResponseApi("Success deleted region",true);
    }
    @Override
    public Optional<Region> findById(Long id) {
        return regionRepository.findById(id);
    }

    @Override
    public List<Region> findAllByCarrierName(String carrierName) {
        return regionRepository.findAllByCarriersName(carrierName);
    }


}
