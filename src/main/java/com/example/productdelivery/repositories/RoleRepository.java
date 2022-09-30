package com.example.productdelivery.repositories;


import com.example.productdelivery.consts.RoleName;
import com.example.productdelivery.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(RoleName roleName);
}
