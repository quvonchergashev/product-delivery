package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String userName);

    Optional<Users> findByPassword(String password);

    boolean existsByUserName(String username);



}
