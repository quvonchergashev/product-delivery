package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.CarrierDto;
import com.example.productdelivery.entity.Carrier;

import java.util.List;

public interface CarrierService {

    List<CarrierDto> notExistCarrier(List<CarrierDto> carrierDtos);

    Carrier save(CarrierDto carrierDto);

    List<String> getCarriersForRegion(String regionName);

    Carrier findByName(String name);



}
