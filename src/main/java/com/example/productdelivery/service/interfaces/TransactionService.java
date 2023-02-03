package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.EvaluateTransactionDto;
import com.example.productdelivery.dto.ScorePerCarrierDto;
import com.example.productdelivery.dto.TransactionDto;
import com.example.productdelivery.entity.Transaction;
import com.example.productdelivery.payload.ResponseApi;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    boolean save(TransactionDto transactionDto);

    boolean evaluateTransaction(EvaluateTransactionDto dto);

    public List<Transaction> findAll();

    List<ScorePerCarrierDto> scorePerCarrier(Integer minimumScore);

    ResponseApi getUserTransactions(Long userId);

    ResponseApi findById(Long id);


    void deleteByUserId(Long id);

    ResponseApi deliveryRegion();
}
