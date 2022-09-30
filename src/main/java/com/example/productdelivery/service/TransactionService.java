package com.example.productdelivery.service;

import com.example.productdelivery.dto.EvaluateTransactionDto;
import com.example.productdelivery.dto.ScorePerCarrierDto;
import com.example.productdelivery.dto.TransactionDto;
import com.example.productdelivery.entity.*;
import com.example.productdelivery.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OfferService offerService;
    private final RequestService requestService;
    private final CarrierService carrierService;

    public boolean save(TransactionDto transactionDto) {
        Offer offer = offerService.findById(transactionDto.getOfferId());
        Requests requests = requestService.findById(transactionDto.getRequestId());
        Carrier carrier = carrierService.findByName(transactionDto.getCarrierName());

        if (offer == null || offer.getProduct() == null ||
                requests == null || requests.getProduct() == null) {
            return false;
        }

        if (offer.getTransaction() != null || requests.getTransaction() != null) {
            return false;
        }

        if (!Objects.equals(offer.getProduct().getId(), requests.getProduct().getId())) {
            return false;
        }

        List<String> placeNames = carrier.getRegion()
                .stream()
                .flatMap(region -> region.getPlaces().stream())
                .map(Place::getName)
                .toList();

        if (!placeNames.contains(offer.getPlaceName()) && !placeNames.contains(requests.getPlaceName())) {
            return false;
        }
        Transaction transaction = new Transaction();
        transaction.setCarrierName(carrier.getName());
        transaction.setOffer(offer);
        transaction.setRequests(requests);
        transaction.setTransactionNumber(transaction.getTransactionNumber()+1);
        transactionRepository.save(transaction);
        offer.setTransaction(transaction);
        requests.setTransaction(transaction);
        offerService.save(offer);
        requestService.save(requests);
        return true;
    }

    public boolean evaluateTransaction(EvaluateTransactionDto dto) {
        Optional<Transaction> byId = transactionRepository.findById(dto.getTransactionId());
        if (byId.isEmpty()) {
            return false;
        }
        if (dto.getScore() >= 1 && dto.getScore() <= 10) {
            byId.get().setScore(dto.getScore());
            transactionRepository.save(byId.get());
            return true;
        }
        return false;
    }
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }

    public List<ScorePerCarrierDto> scorePerCarrier(Integer minimumScore) {
        String carrier=null;
        List<ScorePerCarrierDto> scorePerCarrierDtos=new ArrayList<>();
        for (Transaction transaction : transactionRepository.findAll()) {
            if (!transaction.getScore().equals(minimumScore)) {
                carrier=transaction.getCarrierName();
                throw new ArithmeticException();
            }
            ScorePerCarrierDto scorePerCarrierDto=new ScorePerCarrierDto();
            Integer allTransactionScore = transactionRepository.findAllTransactionScore(carrier);
            scorePerCarrierDto.setCarrier(carrierService.findByName(carrier));
            scorePerCarrierDto.setScore(allTransactionScore);
            scorePerCarrierDtos.add(scorePerCarrierDto);
        }
        return scorePerCarrierDtos;

    }
}
