package com.example.productdelivery.controller;

import com.example.productdelivery.dto.RoleAddDto;
import com.example.productdelivery.dto.SetPermissionDto;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/set-permission")
    public ResponseEntity<?> setPermission(@RequestBody SetPermissionDto setPermissionDto){
        ResponseApi add = roleService.setPermission(setPermissionDto);
        if (add.isSuccess()) return ResponseEntity.ok(add);
        return ResponseEntity.status(409).body(add);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody RoleAddDto roleAddDto){
        ResponseApi add = roleService.add(roleAddDto);
        if (add.isSuccess()) return ResponseEntity.ok(add);
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping("/find-all-role")
    public ResponseEntity<?> findAllRole(){
        ResponseApi all = roleService.findAll();
        if (all.isSuccess()) return ResponseEntity.ok(all);
        return ResponseEntity.status(409).body(all);
    }

    @PostMapping("/edit-role")
    public ResponseEntity<?> edit(@RequestBody Roles roles){
        ResponseApi edit = roleService.edit(roles);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }
    @PostMapping("/delete-role/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseApi responseApi = roleService.deleteById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }
}
