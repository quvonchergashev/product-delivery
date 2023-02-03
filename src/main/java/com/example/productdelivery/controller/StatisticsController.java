package com.example.productdelivery.controller;

import com.example.productdelivery.dto.ScorePerCarrierDto;

import com.example.productdelivery.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasRole('STATISTICS')")
@RequestMapping("/statistics")
public class StatisticsController {

    private final TransactionService transactionService;

    @GetMapping("/delivery-regions")
    public ResponseEntity<?> deliveryRegionsPerNT(){
        return ResponseEntity.ok(transactionService.deliveryRegion());
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
