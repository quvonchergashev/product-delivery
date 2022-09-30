package com.example.productdelivery.service;
import com.example.productdelivery.dto.RequestDto;
import com.example.productdelivery.entity.Offer;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.Requests;
import com.example.productdelivery.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final PlaceService placeService;
    private final ProductService productService;

    public boolean save(RequestDto requestDto){
        var placeName = requestDto.getPlaceName();
        var productId = requestDto.getProductId();

        Product byId = productService.findById(productId);
        Place byName = placeService.findByName(placeName);

        if (byName == null || byId==null ) {
            return false;
        }
        Requests request=new Requests();
        request.setPlaceName(placeName);
        request.setProduct(byId);
        requestRepository.save(request);
        return true;
    }

    public Requests findById(Long id){
        if (id == null) return null;
        return requestRepository.findById(id).orElse(null);
    }

    public Requests save(Requests requests){
        return requestRepository.save(requests);
    }

}
