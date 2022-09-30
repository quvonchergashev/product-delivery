package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Carrier;
import com.example.productdelivery.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
