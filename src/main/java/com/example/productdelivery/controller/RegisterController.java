package com.example.productdelivery.controller;

import com.example.productdelivery.dto.LoginDto;
import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RegisterController {


    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register( @RequestBody RegisterDto registerDto)throws IOException {
        if (userService.register(registerDto)) return ResponseEntity.ok("Success");
        return ResponseEntity.status(409).body("Not Found");
    }
    @GetMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        if (userService.login(loginDto)) return ResponseEntity.ok("Success");
        return ResponseEntity.status(409).body("password/username not found");
    }
}
