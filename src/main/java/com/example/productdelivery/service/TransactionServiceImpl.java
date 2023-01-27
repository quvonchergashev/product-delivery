package com.example.productdelivery.service;

import com.example.productdelivery.dto.EvaluateTransactionDto;
import com.example.productdelivery.dto.ScorePerCarrierDto;
import com.example.productdelivery.dto.TransactionDto;
import com.example.productdelivery.entity.*;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.TransactionRepository;
import com.example.productdelivery.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final OfferService offerService;
    private final RequestService requestService;
    private final CarrierService carrierService;
    private final UserService userService;



    @Override
    public boolean save(TransactionDto transactionDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId = userService.findById(principal.getId());
        Optional<Offer> offer = offerService.findById(transactionDto.getOfferId());
        Optional<Requests> requests = requestService.findById(transactionDto.getRequestId());
        Carrier carrier = carrierService.findByName(transactionDto.getCarrierName());


        if (offer.isEmpty() || offer.get().getProduct()==null ||
                requests.isEmpty() || requests.get().getProduct()==null){
            return false;
        }

        if (!transactionRepository.findByOffer_Id(offer.get().getId()).isEmpty() ||
        !transactionRepository.findByRequests_Id(requests.get().getId()).isEmpty()) {
            return false;
        }

        if (!Objects.equals(offer.get().getProduct().getId(), requests.get().getProduct().getId())) {
            return false;
        }

        List<String> placeNames = carrier.getRegion()
                .stream()
                .flatMap(region -> region.getPlaces().stream())
                .map(Place::getName)
                .toList();

        if (!placeNames.contains(offer.get().getPlaceName()) && !placeNames.contains(requests.get().getPlaceName())) {
            return false;
        }
        Transaction transaction = new Transaction();
        transaction.setCarrierName(carrier.getName());
        transaction.setOffer(offer.get());
        transaction.setRequests(requests.get());
        transaction.setTransactionNumber(transaction.getTransactionNumber() + 1);
        transaction.setUser(byId.get());
        transactionRepository.save(transaction);
        return true;
    }
    @Override
    public ResponseApi getUserTransactions(Long userId) {
        Optional<User> byId = userService.findById(userId);
        if (byId.isEmpty()) return new ResponseApi("Not found user",false);
        List<Transaction> allByUserId = transactionRepository.findAllByUserId(userId);
        return new ResponseApi("Success",true,allByUserId);
    }

    @Override
    public ResponseApi findById(Long id) {
        Optional<Transaction> byId = transactionRepository.findById(id);
        if (byId.isEmpty()) {
            return new ResponseApi("Not found transaction",false);
        }
        return new ResponseApi("Success",true,byId.get());
    }

    @Override
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
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    @Override
    public List<ScorePerCarrierDto> scorePerCarrier(Integer minimumScore) {
        String carrier = null;
        List<ScorePerCarrierDto> scorePerCarrierDtos = new ArrayList<>();
        for (Transaction transaction : transactionRepository.findAll()) {
            if (!transaction.getScore().equals(minimumScore)) {
                carrier = transaction.getCarrierName();
            }
            ScorePerCarrierDto scorePerCarrierDto = new ScorePerCarrierDto();
            Integer allTransactionScore = transactionRepository.findAllTransactionScore(carrier);
            scorePerCarrierDto.setCarrier(carrierService.findByName(carrier));
            scorePerCarrierDto.setScore(allTransactionScore);
            scorePerCarrierDtos.add(scorePerCarrierDto);
        }
        return scorePerCarrierDtos;

    }
}
