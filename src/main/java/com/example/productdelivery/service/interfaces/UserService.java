package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.RegisterDto;
import com.example.productdelivery.dto.UserEditDto;
import com.example.productdelivery.dto.UserForRoleDto;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    ResponseApi registerUser(RegisterDto userDto, MultipartHttpServletRequest request) throws IOException;
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    ResponseApi edit(UserEditDto userEditDto);
    ResponseApi findAll();
    Optional<User> findByRoleName(String roleName);
    ResponseApi userForRole(UserForRoleDto userForRoleDto);

}
