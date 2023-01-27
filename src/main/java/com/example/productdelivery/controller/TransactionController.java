package com.example.productdelivery.controller;

import com.example.productdelivery.dto.EvaluateTransactionDto;
import com.example.productdelivery.dto.TransactionDto;
import com.example.productdelivery.entity.Transaction;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasRole('ROLE_CARRIER')")
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize(value = "hasRole('ROLE_CARRIER')")
    @PostMapping("/add-transaction")
    public ResponseEntity<String> addTransaction(
            @RequestBody TransactionDto transactionDto
    ){
        boolean save = transactionService.save(transactionDto);
        if (save) return new ResponseEntity<>("Success", HttpStatus.OK);
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/evaluate-transaction")
    public boolean evaluateTransaction(
            @RequestBody EvaluateTransactionDto dto
    ){
        return transactionService.evaluateTransaction(dto);
    }

    @GetMapping("/get-user-transactions/{userId}")
    public ResponseEntity<?> getUserTransactions(@PathVariable Long userId){
        ResponseApi userTransactions = transactionService.getUserTransactions(userId);
        if (userTransactions.isSuccess()) return ResponseEntity.ok(userTransactions);
        return ResponseEntity.status(409).body(userTransactions);
    }
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        List<Transaction> all = transactionService.findAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        ResponseApi byId = transactionService.findById(id);
        if (byId.isSuccess()) return ResponseEntity.ok(byId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(byId);
    }


}
