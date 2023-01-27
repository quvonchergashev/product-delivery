package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.RoleAddDto;
import com.example.productdelivery.dto.SetPermissionDto;
import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.payload.ResponseApi;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByName(String roleName);
    ResponseApi setPermission(SetPermissionDto setPermissionDto);
    ResponseApi edit(Roles roles);
    ResponseApi deleteById(Long id);
    ResponseApi findAll();
    Roles save(Roles roles);
    void deletePermissionById(Permission permission);
    Optional<Roles> findById(Long id);
    ResponseApi add(RoleAddDto roleAddDto);
}
