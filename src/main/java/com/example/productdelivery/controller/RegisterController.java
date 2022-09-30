package com.example.productdelivery.controller;

import com.example.productdelivery.Message.ApiResult;
import com.example.productdelivery.dto.ResponseDto;
import com.example.productdelivery.entity.Users;
import com.example.productdelivery.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/sign-up")
    public ApiResult signUp(@RequestBody Users users){
        return registerService.signUp(users);
    }
    @GetMapping("/sign-in")
    public ApiResult signIn(@RequestBody ResponseDto responseDto){
        return registerService.singIn(responseDto);
    }
}
