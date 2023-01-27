package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
//    Roles findByName(String name);
    boolean existsByName(String name);

}
