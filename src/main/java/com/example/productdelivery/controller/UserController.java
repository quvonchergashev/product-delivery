package com.example.productdelivery.controller;

import com.example.productdelivery.dto.UserForRoleDto;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
@RequestMapping("user")
public class UserController {

    private final UserService userService;


    @PreAuthorize(value = "hasAnyAuthority('USER_READ')")
    @GetMapping("find-all")
    public ResponseEntity<?> findAll() {
        ResponseApi all = userService.findAll();
        if (all.isSuccess()) return ResponseEntity.ok(all);
        return ResponseEntity.status(409).body(all);
    }
    @PostMapping("set-role")
    public ResponseEntity<?> setRole(@RequestBody UserForRoleDto userForRoleDto){
        ResponseApi responseApi = userService.userForRole(userForRoleDto);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }










}
