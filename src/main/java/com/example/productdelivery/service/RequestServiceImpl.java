package com.example.productdelivery.service;

import com.example.productdelivery.dto.RequestDto;
import com.example.productdelivery.entity.Place;
import com.example.productdelivery.entity.Product;
import com.example.productdelivery.entity.Requests;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.RequestRepository;
import com.example.productdelivery.service.interfaces.PlaceService;
import com.example.productdelivery.service.interfaces.ProductService;
import com.example.productdelivery.service.interfaces.RequestService;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final PlaceService placeService;
    private final ProductService productService;

    private final UserService userService;

    @Override
    public boolean save(RequestDto requestDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId1 = userService.findById(principal.getId());
        var placeName = requestDto.getPlaceName();
        var productId = requestDto.getProductId();

        Optional<Product> byId = productService.findById(productId);
        Place byName = placeService.findByName(placeName);

        if (byName == null || byId.isEmpty()) {
            return false;
        }
        Requests request = new Requests();
        request.setPlaceName(placeName);
        request.setUser(byId1.get());
        request.setProduct(byId.get());
        requestRepository.save(request);
        return true;
    }
    @Override
    public Optional<Requests> findById(Long id) {
        return requestRepository.findById(id);
    }
    @Override
    public Requests save(Requests requests) {
        return requestRepository.save(requests);
    }
    @Override
    public List<Requests> findAll() {
        return requestRepository.findAll();
    }
    @Override
    public ResponseApi deleteById(Long id) {
        Optional<Requests> byId = requestRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found request",false);
        requestRepository.deleteById(id);
        return new ResponseApi("Success",true);
    }
    @Override
    public ResponseApi edit(Requests requests) {
        Optional<Requests> byId = requestRepository.findById(requests.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found request",false);
        Requests save = requestRepository.save(requests);
        return new ResponseApi("Succes",true,save);

    }
    @Override
    public ResponseApi getUserRequests(Long userId) {
        Optional<User> byId = userService.findById(userId);
        if (byId.isEmpty()) return new ResponseApi("Not found user id="+userId,false);
        List<Requests> allByUserId = requestRepository.findAllByUserId(userId);
        return new ResponseApi("Succes",true,allByUserId);
    }

}
