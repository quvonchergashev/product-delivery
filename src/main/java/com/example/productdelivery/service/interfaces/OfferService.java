package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.OfferDto;
import com.example.productdelivery.dto.OfferEditDto;
import com.example.productdelivery.entity.Offer;
import com.example.productdelivery.payload.ResponseApi;

import java.util.Optional;

public interface OfferService {
    ResponseApi getOfferById(Long id);
    ResponseApi  getOffers();
    boolean save(OfferDto offerDto);
    Offer save(Offer offer);
    Optional<Offer> findById(Long id);
    ResponseApi deleteById(Long id);
    ResponseApi edit(OfferEditDto offerEditDto);
    ResponseApi getUserOffers(Long id);


}
