package com.example.productdelivery.service;

import com.example.productdelivery.dto.RoleAddDto;
import com.example.productdelivery.dto.SetPermissionDto;
import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.RoleRepository;
import com.example.productdelivery.service.interfaces.PermissionService;
import com.example.productdelivery.service.interfaces.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionService permissionService;

    public RoleServiceImpl(RoleRepository roleRepository, @Lazy PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Optional<Roles> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public ResponseApi setPermission(SetPermissionDto setPermissionDto) {
        if (setPermissionDto.getPermissionsId() == null || setPermissionDto.getPermissionsId().isEmpty()) {
            for (Roles roles : roleRepository.findAll()) {
                if (roleRepository.existsById(roles.getId())) {
                    return new ResponseApi("such a role exists", false);
                }
            }
            Roles roles = new Roles();
            roles.setName(roleRepository.findById(setPermissionDto.getRoleId()).get().getName());
            roleRepository.save(roles);
            return new ResponseApi("Success created role", true);
        }
        if (setPermissionDto.getRoleId() == null ) {
            return new ResponseApi("If there is no role, we cannot give you permission", false);
        }
        Optional<Roles> role = roleRepository.findById(setPermissionDto.getRoleId());
        if (!role.isEmpty()) {
            List<Long> longs = role.get().getPermissions().stream().map(permission -> permission.getId()).toList();
            if (longs.isEmpty()) {
                for (Long aLong : setPermissionDto.getPermissionsId()) {
                    role.get().getPermissions().add(permissionService.findById(aLong).get());
                }
            }
            List<Permission> permissions = role.get().getPermissions();
                for (Long aLong : longs) {
                    if (!permissionService.existsPermissionById(aLong)) {
                        permissions.add(permissionService.findById(aLong).get());
                    }
                }

            roleRepository.save(role.get());
            return new ResponseApi("Succes add permission", true, role);
        }
        Roles roles = new Roles();
        roles.setName(roleRepository.findById(setPermissionDto.getRoleId()).get().getName());
        List<Permission> all = permissionService.findAll();
        List<Permission> permissions = new ArrayList<>();
        List<Long> permissionsId = setPermissionDto.getPermissionsId();
        if (!permissionsId.isEmpty()) {
            for (Long aLong : permissionsId) {
                Optional<Permission> byId = permissionService.findById(aLong);
                if (all.contains(byId.get())) {
                    permissions.add(byId.get());
                }
            }
        }
        roles.setPermissions(permissions);
        Roles save = roleRepository.save(roles);
        return new ResponseApi("Success create role added to permission", true, save);
    }

    @Override
    public ResponseApi edit(Roles roles) {
        Optional<Roles> byId = roleRepository.findById(roles.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found role", false);
        Roles save = roleRepository.save(roles);
        return new ResponseApi("Success edited role", true, save);
    }

    @Override
    public ResponseApi deleteById(Long id) {
        Optional<Roles> byId = roleRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found role", false);
        roleRepository.deleteById(id);
        return new ResponseApi("Success deleted role", true);
    }

    @Override
    public ResponseApi findAll() {
        List<Roles> all = roleRepository.findAll();
        return new ResponseApi("Success", true, all);
    }

    @Override
    public Roles save(Roles roles) {
        return roleRepository.save(roles);
    }
    @Override
    public void deletePermissionById(Permission permission) {
        List<Roles> all = roleRepository.findAll();
        for (Roles role : all) {
            role.getPermissions().remove(permission);
        }
    }

    @Override
    public Optional<Roles> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public ResponseApi add(RoleAddDto roleAddDto) {
        Optional<Roles> byName = roleRepository.findByName(roleAddDto.getRoleName());
        if (byName.isEmpty()) {
            Roles roles = new Roles();
            roles.setName(roleAddDto.getRoleName());
            Roles save = roleRepository.save(roles);
            return new ResponseApi("Success", true, save);
        }
        return new ResponseApi("Such a role exists",false);
    }
}
