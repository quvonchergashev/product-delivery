package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.payload.ResponseApi;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    ResponseApi add(Permission permission);
    ResponseApi edit(Permission permission);
    ResponseApi deleteById(Long id);
    ResponseApi getAllPermission();
    Optional<Permission> findById(Long id);
    List<Permission> findAll();

    boolean existsPermissionById(Long permissionId);
}
