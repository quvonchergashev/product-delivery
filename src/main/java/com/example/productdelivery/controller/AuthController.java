package com.example.productdelivery.controller;

import com.example.productdelivery.dto.LoginDto;
import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.AuthService;
import com.example.productdelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        ResponseApi login = authService.login(loginDto);
        if (login.isSuccess()) return ResponseEntity.ok(login);
        return ResponseEntity.status(409).body(login);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto)throws IOException {
        ResponseApi responseApi = userService.registerUser(registerDto);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

}
