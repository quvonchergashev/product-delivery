package com.example.productdelivery.repositories;
import com.example.productdelivery.entity.Region;
import com.example.productdelivery.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query(value = "select sum(t.score) from transaction where t.carrier_name:name", nativeQuery = true)
    Integer findAllTransactionScore(String name);
    List<Transaction> findAllByUserId(Long user_id);
    Optional<Transaction> findByOffer_Id(Long offer_id);
    Optional<Transaction> findByRequests_Id(Long requests_id);

}
