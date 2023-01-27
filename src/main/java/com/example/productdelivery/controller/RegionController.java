package com.example.productdelivery.controller;

import com.example.productdelivery.dto.PlaceDto;
import com.example.productdelivery.dto.RegionDto;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.PlaceService;
import com.example.productdelivery.service.interfaces.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    private final PlaceService placeService;


    @PreAuthorize(value = "hasAnyAuthority('REGION_CREATE','PLACE_CREATE')")
    @PostMapping("/add-region")
    public List<PlaceDto> add(@RequestBody RegionDto regionDto) {
        var placeList = placeService.notExistPlaces(regionDto.getPlaces())
                .stream()
                .map(PlaceDto::getName)
                .toList();

        var save = regionService.save(regionDto);
        return save
                .getPlaces()
                .stream()
                .filter(place -> placeList.contains(place.getName()))
                .map(PlaceDto::new)
                .sorted(Comparator.comparing(PlaceDto::getName))
                .toList();
    }

    @PreAuthorize(value = "hasAnyAuthority('REGION_EDIT')")
    @PostMapping("/edit-region")
    public ResponseEntity<?> edit(@RequestBody Region region) {
        ResponseApi edit = regionService.edit(region);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }

        @PreAuthorize(value = "hasAnyAuthority('REGION_DELETE')")
    @PostMapping("/delete-region/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseApi responseApi = regionService.deleteById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

    @GetMapping("/get-regions-and-places")
    public ResponseEntity<?> getRegionsAndPlaces(){
        return ResponseEntity.ok(regionService.findAll());
    }
}
