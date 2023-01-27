package com.example.productdelivery.controller;

import com.example.productdelivery.dto.PlaceEditDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @PreAuthorize(value = "hasAnyAuthority('PLACE_EDIT')")
    @PostMapping("/edit-place")
    public ResponseEntity<?> editPlace(@RequestBody PlaceEditDto placeEditDto){
        ResponseApi edit = placeService.edit(placeEditDto);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }
    @PreAuthorize(value = "hasAnyAuthority('PLACE_DELETE')")
    @PostMapping("/delete-find-by-id/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id){
        ResponseApi responseApi = placeService.deleteFindById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

}
