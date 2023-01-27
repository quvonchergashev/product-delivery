package com.example.productdelivery.service;

import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.PermissionRepository;
import com.example.productdelivery.repositories.UserRepository;
import com.example.productdelivery.service.interfaces.PermissionService;
import com.example.productdelivery.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    private final RoleService roleService;
    private final UserRepository userService;


    @Override
    public ResponseApi add(Permission permission) {
        for (Permission permission1 : permissionRepository.findAll()) {
            if (permission1.getName().equalsIgnoreCase(permission.getName())) {
                return new ResponseApi("Permission with this name exists", false);
            }
        }
        Optional<User> super_admin = userService.findByRoleName("ROLE_SUPER_ADMIN");
        Roles role = super_admin.get().getRole();
        role.getPermissions().add(permission);
        permissionRepository.save(permission);
        return new ResponseApi("Success permission added", true);
    }

    @Override
    public ResponseApi edit(Permission permission) {
        Optional<Permission> byId = permissionRepository.findById(permission.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found permission", false);
        permissionRepository.save(permission);
        return new ResponseApi("Success edited permission", true);
    }

    @Override
    public ResponseApi deleteById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permisson not found"));
        roleService.deletePermissionById(permission);
        Optional<Permission> byId = permissionRepository.findById(id);
        if (byId.isEmpty()) return new ResponseApi("Not found permission", false);
        permissionRepository.deleteById(id);
        return new ResponseApi("Success deleted permission", true);
    }

    @Override
    public ResponseApi getAllPermission() {
        List<Permission> all = permissionRepository.findAll();
        if (all.isEmpty()) {
            return new ResponseApi("Permission empty", false);
        }
        return new ResponseApi("Success", true, all);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public boolean existsPermissionById(Long permissionId) {
        return permissionRepository.existsPermissionById(permissionId);
    }
}
