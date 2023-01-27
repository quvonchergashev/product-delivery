package com.example.productdelivery.service;

import com.example.productdelivery.dto.OfferDto;
import com.example.productdelivery.dto.OfferEditDto;
import com.example.productdelivery.entity.Offer;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.OfferRepository;
import com.example.productdelivery.service.interfaces.OfferService;
import com.example.productdelivery.service.interfaces.PlaceService;
import com.example.productdelivery.service.interfaces.ProductService;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final PlaceService placeService;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public boolean save(OfferDto offerDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId1 = userService.findById(principal.getId());
        String placeName = offerDto.getPlaceName();
        Long productId = offerDto.getProductId();

        Place byName = placeService.findByName(placeName);
        Optional<Product> byId = productService.findById(productId);

        if (byId.isEmpty() || byName == null) {
            return false;
        }
        Offer offer = new Offer();
        offer.setPlaceName(placeName);
        offer.setUser(byId1.get());
        offer.setProduct(byId.get());
        offerRepository.save(offer);
        return true;
    }

    @Override
    public ResponseApi getOfferById(Long id) {
        Optional<Offer> byId = offerRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found offer id=" + id, false);
        return new ResponseApi("Success", true, byId.get());
    }

    @Override
    public ResponseApi getOffers() {
        List<Offer> all = offerRepository.findAll();
        return new ResponseApi("Success", true, all);
    }
    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public ResponseApi deleteById(Long id) {
        Optional<Offer> byId = offerRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found offer id="+id,false);
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId1 = userService.findById(principal.getId());
        if (byId.get().getUser().getId().equals(byId1.get().getId())) {

            offerRepository.deleteById(id);
            return new ResponseApi("Success",true);
        }
        return new ResponseApi("Error",false);
    }
    @Override
    public ResponseApi edit(OfferEditDto offerEditDto) {
        Optional<Offer> byId = offerRepository.findById(offerEditDto.getOfferId());
        if (byId.isEmpty()) return new ResponseApi("Not found offer",false);
        byId.get().setOfferText(offerEditDto.getOfferText());
        Offer save = offerRepository.save(byId.get());
        return new ResponseApi("Success",true,save);
    }

    @Override
    public ResponseApi getUserOffers(Long id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found user id="+id,false);
        List<Offer> allByUserId = offerRepository.findAllByUserId(id);
        return new ResponseApi("Success",true,allByUserId);
    }

}
