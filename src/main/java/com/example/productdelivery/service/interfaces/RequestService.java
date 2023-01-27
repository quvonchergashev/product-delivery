package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.RequestDto;
import com.example.productdelivery.entity.Requests;
import com.example.productdelivery.payload.ResponseApi;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    boolean save(RequestDto requestDto);

    Optional<Requests> findById(Long id);

    Requests save(Requests requests);

    List<Requests> findAll();

    ResponseApi deleteById(Long id);

    ResponseApi edit(Requests requests);

    ResponseApi getUserRequests(Long userId);
}
