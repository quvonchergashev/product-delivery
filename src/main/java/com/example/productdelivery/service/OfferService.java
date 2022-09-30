package com.example.productdelivery.service;

import com.example.productdelivery.dto.OfferDto;
import com.example.productdelivery.entity.Offer;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.Requests;
import com.example.productdelivery.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    private final PlaceService placeService;

    private final ProductService productService;

    public boolean save(OfferDto offerDto) {
        String placeName = offerDto.getPlaceName();
        Long productId = offerDto.getProductId();

        Place byName = placeService.findByName(placeName);
        Product byId = productService.findById(productId);

        if(byId == null || byName == null){
            return false;
        }
        Offer offer=new Offer();
        offer.setPlaceName(placeName);
        offer.setProduct(byId);
        offerRepository.save(offer);
        return true;
    }

    public Offer findById(Long id){
        if (id == null) return null;
        return offerRepository.findById(id).orElse(null);
    }
    public Offer save(Offer offer){
        return offerRepository.save(offer);
    }
}
