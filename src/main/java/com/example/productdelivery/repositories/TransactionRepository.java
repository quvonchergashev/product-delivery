package com.example.productdelivery.repositories;
import com.example.productdelivery.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "select sum(t.score) from transaction where t.carrier_name:name", nativeQuery = true)
    Integer findAllTransactionScore(String name);
}
