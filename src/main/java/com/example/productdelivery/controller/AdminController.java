package com.example.productdelivery.controller;

import com.example.productdelivery.consts.PasswordEncode;
import com.example.productdelivery.dto.RegisterAdminDto;
import com.example.productdelivery.dto.UpdateStatusDto;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize(value = "hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/add-admin",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAdmin(
            MultipartHttpServletRequest request,
             RegisterAdminDto registerDto
    ) throws IOException {
        ResponseApi add = adminService.add(registerDto, request);
        if (add.isSuccess()) return ResponseEntity.ok(add);
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping("/all-password-encode")
    public List<PasswordEncode> allPasswordEncode(
    ){
        List<PasswordEncode> passwordEncodes=new ArrayList<>();
        passwordEncodes.add(PasswordEncode.bcrypt);
        passwordEncodes.add(PasswordEncode.scrypt);
        return passwordEncodes;
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(
            @RequestBody UpdateStatusDto updateStatusDto
            ){
        ResponseApi responseApi = adminService.updateStatus(updateStatusDto);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }
    @GetMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        ResponseApi delete = adminService.delete(id);
        if (delete.isSuccess()) return ResponseEntity.ok(delete);
        return ResponseEntity.status(409).body(delete);
    }
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody User user){
        ResponseApi edit = adminService.edit(user);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }


}
