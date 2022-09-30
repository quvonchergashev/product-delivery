package com.example.productdelivery.service;

import com.example.productdelivery.consts.RoleName;
import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.RoleRepository;
import com.example.productdelivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    public ResponseApi registerUser(RegisterDto userDto) throws IOException {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber) return new ResponseApi("User already have", false);
        if (!userDto.getPassword().equals(userDto.getPrePassword()))
            return new ResponseApi("Passwords don't match", false);
        Optional<Roles> roleName = roleRepository.findByRoleName(RoleName.ROLE_USER);
        if (!roleName.isPresent()) return new ResponseApi("Role Not found", false);
        Roles roles = roleName.get();

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setRoles(Collections.singletonList(roles));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
        return new ResponseApi("Successfully registered", true);
    }
    public Page<User> getAllUser(int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<User> userRepositoryAll = userRepository.findAll(pageRequest);
        return userRepositoryAll;
    }

    public User getById(UUID id) {
        Optional<User> repository = userRepository.findById(id);
        return repository.orElse(null);
    }

    public User getByPhoneNumber(String phoneNumber) {
        Optional<User> phoneNumber1 = userRepository.findByPhoneNumber(phoneNumber);
        return phoneNumber1.orElse(null);
    }
}
