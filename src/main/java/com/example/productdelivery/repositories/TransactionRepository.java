package com.example.productdelivery.repositories;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findByName(String soato);

    @Query(value = "select sum(t.score) from transaction where t.carrier_name:name", nativeQuery = true)
    Integer findAllTransactionScore(String name);
}
