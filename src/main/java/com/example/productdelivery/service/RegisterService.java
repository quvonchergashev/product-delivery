package com.example.productdelivery.service;

import com.example.productdelivery.Message.ApiResult;
import com.example.productdelivery.dto.ResponseDto;
import com.example.productdelivery.entity.Users;
import com.example.productdelivery.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    public ApiResult signUp(Users user){
        List<Users> all = userRepository.findAll();
        for (Users users : all) {
            if (users.getUsername().equals(user.getUsername())) {
                return new ApiResult(false, "username unique, pleace another enter username!!!");
            }
        }
        userRepository.save(user);
        return new ApiResult(true,"User added");
    }
    public ApiResult singIn(ResponseDto responseDto){
        List<Users> all = userRepository.findAll();
        for (Users users : all) {
            if (!users.getUsername().equals(responseDto.getUsername())
            || !users.getPassword().equals(responseDto.getPassword())) {
                return new ApiResult(false,"username/password error");
            }
        }
        return new ApiResult(true,"Welcome system");
    }
}
