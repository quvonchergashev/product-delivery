package com.example.productdelivery.service;

import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.dto.UserEditDto;
import com.example.productdelivery.dto.UserForRoleDto;
import com.example.productdelivery.entity.Attachment;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.AttachmentRepository;
import com.example.productdelivery.repositories.UserRepository;
import com.example.productdelivery.service.interfaces.RoleService;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private static final String uploadDirectory = "usersImages";
    @Override
    public ResponseApi registerUser(RegisterDto userDto, MultipartHttpServletRequest request) throws IOException {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber) return new ResponseApi("User already have", false);
        if (!userDto.getPassword().equals(userDto.getPrePassword()))
            return new ResponseApi("Passwords don't match", false);
        Optional<Roles> roleName = roleService.findByName("ROLE_USER");
        if (roleName.isEmpty()) return new ResponseApi("Not found role",false);
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {
            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            String[] split = file.getOriginalFilename().split("\\.");
            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
            attachment.setName(name);
            attachmentRepository.save(attachment);

            Path path = Paths.get(uploadDirectory + "/" + name);
            Files.copy(file.getInputStream(), path);

            User user = new User();
            user.setRole(roleName.get());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setImage(file.getOriginalFilename());
            user.setUsername(userDto.getUsername());
            user.setLastname(userDto.getLastname());
            user.setFirstname(userDto.getFirstname());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
        }
        return new ResponseApi("Successfully registered", true);
    }
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
         userRepository.deleteById(id);
    }

    @Override
    public ResponseApi edit(UserEditDto userEditDto) {
        Optional<User> byId = userRepository.findById(userEditDto.getId());
        if (byId.isEmpty()) return new ResponseApi("Not found user",false);
        userRepository.save(byId.get());
        return new ResponseApi("Success edited", true);
    }

    @Override
    public ResponseApi findAll() {
        List<User> all = userRepository.findAll();
        return new ResponseApi("Success", true, all);
    }

    @Override
    public ResponseApi userForRole(UserForRoleDto userForRoleDto) {
        if (userForRoleDto.getRoleId().isEmpty()||userForRoleDto.getUserId().isEmpty()) {
            return new ResponseApi("Error",false);
        }
        for (Long userId : userForRoleDto.getUserId()) {
            if (userRepository.existsUserById(userId)) {
                for (Long roleId : userForRoleDto.getRoleId()) {
                    Roles roles = roleService.findById(roleId).get();
                    Optional<User> byId = userRepository.findById(userId);
                    User user = byId.get();
                    user.setRole(roles);
                    userRepository.save(user);
                }
            }
        }
        return new ResponseApi("Succes",true);
    }

    @Override
    public Optional<User> findByRoleName(String roleName) {
        return userRepository.findByRoleName(roleName);
    }








}
