package com.example.productdelivery.service;

import com.example.productdelivery.dto.CarrierDto;
import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.repositories.CarrierRepository;
import com.example.productdelivery.service.interfaces.CarrierService;
import com.example.productdelivery.service.interfaces.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;

    private final RegionService regionService;

    public List<CarrierDto> notExistCarrier(List<CarrierDto> carrierDtos) {
        return carrierDtos.stream()
                .filter((carrier) -> !carrierRepository.existsByName(carrier.getName()))
                .toList();
    }
   public Carrier save(CarrierDto carrierDto){
       var name = carrierDto.getName();
       var regionDtos = carrierDto.getRegionNames();

       var carrier=new Carrier();
       carrier.setName(name);

       if (carrierRepository.existsByName(name)) {
           carrier=carrierRepository.findByName(name);
       }
       Carrier finalCarrier=carrier;
       List<String> existingNames = carrier.getRegion().stream().map(Region::getName).toList();
       List<Region> regions =regionDtos.stream()
               .filter(region -> !existingNames.contains(region))
               .map(regionService::findByName)
               .filter(Objects::nonNull)
               .toList();

       finalCarrier.getRegion().addAll(regions);
       return carrierRepository.save(carrier);
   }
    @Transactional
    public List<String> getCarriersForRegion(String regionName){
        var carriers = carrierRepository.findCarriersByRegion_Name(regionName);
       return carriers.
               stream()
               .map(p -> new String(p.getName())).toList();
    }

    public Carrier findByName(String name){return carrierRepository.findByName(name);}

    @Override
    public void findAllByRegion_Id(Long regionId) {
        for (Carrier carrier : carrierRepository.findAllByRegion_Id(regionId)) {
            carrierRepository.deleteById(carrier.getId());
        }

    }

}
