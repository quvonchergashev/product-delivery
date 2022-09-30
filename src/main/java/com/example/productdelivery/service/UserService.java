package com.example.productdelivery.service;

import com.example.productdelivery.dto.LoginDto;
import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.entity.Users;
import com.example.productdelivery.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean register(RegisterDto users){
        Optional<Users> byUserName = userRepository.findByUserName(users.getUserName());
        if (!byUserName.isEmpty()) {
            return false;
        }
        Users users1=new Users();
        users1.setUserName(users.getUserName());
        users1.setPassword(users.getPassword());
        userRepository.save(users1);
        return true;
    }

    public boolean login(LoginDto users){
        Optional<Users> byUserName = userRepository.findByUserName(users.getUserName());
        Optional<Users> byPassword = userRepository.findByPassword(users.getPassword());
        if (byUserName.isEmpty()||byPassword.isEmpty()) {
            return false;
        }
        return true;
    }
}
