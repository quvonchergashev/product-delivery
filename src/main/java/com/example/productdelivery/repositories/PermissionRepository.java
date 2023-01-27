package com.example.productdelivery.repositories;

import com.example.productdelivery.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsPermissionById(Long id);
}
