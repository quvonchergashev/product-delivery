package com.example.productdelivery.controller;

import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
@RequestMapping("/admin/permission")
public class PermissionController {

    private final PermissionService permissionService;


//    @PreAuthorize(value = "hasAnyAuthority('ADD_PERMISSION')")
    @PostMapping("/add-permission")
    public ResponseEntity<?> addPermission(@RequestBody Permission permission){
        ResponseApi add = permissionService.add(permission);
        if (add.isSuccess()) return ResponseEntity.ok(add);
        return ResponseEntity.status(409).body(add);
    }
//    @PreAuthorize(value = "hasAnyAuthority('EDIT_PERMISSION')")
    @PostMapping("/edit-permission")
    public ResponseEntity<?> editPermission(@RequestBody Permission permission){
        ResponseApi edit = permissionService.edit(permission);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }

//    @PreAuthorize(value = "hasAnyAuthority('DELETE_PERMISSION')")
    @PostMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        ResponseApi responseApi = permissionService.deleteById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }
//        @PreAuthorize(value = "hasAnyAuthority('FIND_ALL_PERMISSION')")
    @GetMapping("/get-all-permission")
    public ResponseEntity<?> getAllPermission(){
        ResponseApi allPermission = permissionService.getAllPermission();
        if (allPermission.isSuccess()) return ResponseEntity.ok(allPermission);
        return ResponseEntity.status(409).body(allPermission);
    }



}
