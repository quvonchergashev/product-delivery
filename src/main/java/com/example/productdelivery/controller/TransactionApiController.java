package com.example.productdelivery.controller;

import java.util.List;
import java.util.Comparator;

import com.example.productdelivery.dto.*;
import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;



@RestController
@RequiredArgsConstructor
public class TransactionApiController {

    private final RegionService regionService;
    private final PlaceService placeService;
    private final CarrierService carrierService;
    private final RequestService requestService;
    private final OfferService offerService;

    private final TransactionService transactionService;


    @PostMapping("/add-region")
    @Transactional
    public List<PlaceDto> addRegion(
            @RequestBody RegionDto regionDto
    ) {
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

    @PostMapping("/add-carrier")
    @Transactional
    public List<String> addCarrier(
            @RequestBody CarrierDto carrierDto
    ) {
        Carrier save = carrierService.save(carrierDto);
        return save
                .getRegion()
                .stream()
                .map(Region::getName)
                .sorted()
                .toList();
    }

    @GetMapping("/get-carriers-for-region/{regionName}")
    @Transactional
    public List<String> getCarriersForRegion(
            @PathVariable String regionName
    ) {
        return carrierService.getCarriersForRegion(regionName);
    }

    @PostMapping("/add-request")
    @Transactional
    public ResponseEntity<String> addRequest(
            @RequestBody RequestDto requestDto
    ) {
        boolean response = requestService.save(requestDto);
        if (response) {
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-offer")
    @Transactional
    public ResponseEntity<String> addOffer(
            @RequestBody OfferDto offerDto
    ){
        boolean save = offerService.save(offerDto);
        if (save) return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        else return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-transaction")
    public ResponseEntity<String> addTransaction(
            @RequestBody TransactionDto transactionDto
    ){
        boolean save = transactionService.save(transactionDto);
        if (save) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
         return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/evaluate-transaction")
    public boolean evaluateTransaction(
            @RequestBody EvaluateTransactionDto dto
    ){
        return transactionService.evaluateTransaction(dto);
    }
}
