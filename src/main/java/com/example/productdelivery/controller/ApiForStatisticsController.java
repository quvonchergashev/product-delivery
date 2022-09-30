package com.example.productdelivery.controller;

import com.example.productdelivery.dto.ScorePerCarrierDto;
import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Transaction;
import com.example.productdelivery.service.CarrierService;
import com.example.productdelivery.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiForStatisticsController {

    private final TransactionService transactionService;

    private final CarrierService carrierService;

    @GetMapping("/delivery-regions/{transactionNumber}")
    public List<String> deliveryRegionsPerNT(
            @PathVariable Integer transactionNumber
    ){
        List<String> regions=new ArrayList<>();
        for (Transaction transaction : transactionService.findAll()) {
            if (transaction.getTransactionNumber().equals(transactionNumber)) {
                regions.add(transaction.getOffer().getPlaceName());
            }
        }
        return regions;
    }

    @GetMapping("/score-per-carrier/{minimumScore}")
    public List<ScorePerCarrierDto> scorePerCarrier(
            @PathVariable Integer minimumScore
    ){
        if(minimumScore<=0||minimumScore>10){
            throw new ArithmeticException();
        }
        return transactionService.scorePerCarrier(minimumScore);
    }
}
