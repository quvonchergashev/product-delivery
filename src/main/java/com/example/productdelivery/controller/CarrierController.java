package com.example.productdelivery.controller;

import com.example.productdelivery.dto.CarrierDto;
import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.service.interfaces.CarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/carrier")
@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
@RequiredArgsConstructor
public class CarrierController {

    private final CarrierService carrierService;

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
}
