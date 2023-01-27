package com.example.productdelivery.controller;

import com.example.productdelivery.dto.OfferDto;
import com.example.productdelivery.dto.OfferEditDto;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CARRIER','ROLE_USER')")
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> addOffer(
            @RequestBody OfferDto offerDto
    ){
        boolean save = offerService.save(offerDto);
        if (save) return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        else return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody OfferEditDto offerEditDto){
        ResponseApi edit = offerService.edit(offerEditDto);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }

    @PostMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        ResponseApi responseApi = offerService.deleteById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

    @PreAuthorize(value = "hasAnyAuthority('OFFER_READ')")
    @GetMapping("/find-all-offer")
    public ResponseEntity<?> findAll(){
        ResponseApi offers = offerService.getOffers();
        if (offers.isSuccess()) return ResponseEntity.ok(offers);
        return ResponseEntity.status(409).body(offers);
    }

    @PreAuthorize(value = "hasAnyAuthority('OFFER_READ')")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        ResponseApi offerById = offerService.getOfferById(id);
        if (offerById.isSuccess()) return ResponseEntity.ok(offerById);
        return ResponseEntity.status(409).body(offerById);
    }

    @GetMapping("/get-user-offers/{userId}")
    public ResponseEntity<?> getUserOffers(@PathVariable Long userId){
        ResponseApi userOffers = offerService.getUserOffers(userId);
        if (userOffers.isSuccess()) return ResponseEntity.ok(userOffers);
        return ResponseEntity.status(409).body(userOffers);
    }



}
