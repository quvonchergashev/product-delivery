package com.example.productdelivery.service;

import com.example.productdelivery.dto.RegisterAdminDto;
import com.example.productdelivery.dto.UpdateStatusDto;
import com.example.productdelivery.dto.UserEditDto;
import com.example.productdelivery.entity.Attachment;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.AttachmentRepository;
import com.example.productdelivery.service.interfaces.AdminService;
import com.example.productdelivery.service.interfaces.RoleService;
import com.example.productdelivery.service.interfaces.TransactionService;
import com.example.productdelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AttachmentRepository attachmentRepository;
    private final UserService userService;
    private final RoleService roleService;

    private final TransactionService transactionService;
    private static final String uploadDirectory="AdminsImages";
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseApi add(RegisterAdminDto registerDto, MultipartHttpServletRequest request) throws IOException {
        boolean existsByPhoneNumber = userService.existsByPhoneNumber(registerDto.getPhoneNumber());
        if (existsByPhoneNumber) return new ResponseApi("User already have", false);
        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ResponseApi("Passwords don't match", false);
        Optional<Roles> roleName = roleService.findByName("ROLE_ADMIN");
        if (roleName.isEmpty()) return new ResponseApi("Role Not found", false);
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
            if (registerDto.getEncodingName().equals("bcrypt")||registerDto.getEncodingName()==null) {
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            }
            else if (registerDto.getEncodingName().equals("scrypt")){
                SCryptPasswordEncoder sCryptPasswordEncoder=new SCryptPasswordEncoder();
                user.setPassword(sCryptPasswordEncoder.encode(registerDto.getPassword()));
            }
            user.setPhoneNumber(registerDto.getPhoneNumber());
            user.setImage(file.getOriginalFilename());
            user.setUsername(registerDto.getUsername());
            user.setLastname(registerDto.getLastname());
            user.setFirstname(registerDto.getFirstname());
            user.setEmail(registerDto.getEmail());
            user.setEncodingName(registerDto.getEncodingName());
            userService.save(user);
        }
        return new ResponseApi("Successfully saved",true);
    }

    @Override
    public ResponseApi delete(Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseApi("Not found user",false);
        }
        transactionService.deleteByUserId(id);
        userService.deleteById(id);
        return new ResponseApi("Success deleted",true);
    }
    @Override
    public ResponseApi updateStatus(UpdateStatusDto updateStatusDto) {
        Optional<User> userOptional = userService.findById(updateStatusDto.getUserId());
        if (userOptional.isEmpty()) {
            return new ResponseApi("Not found user", false);
        }
        String s = updateStatusDto.getStatus().toUpperCase();

        if (!userOptional.get().getStatus().equals(s)) {
            userOptional.get().setStatus(s);
            userService.save(userOptional.get());
        }
        return new ResponseApi("Success edit status",true);
    }

}
