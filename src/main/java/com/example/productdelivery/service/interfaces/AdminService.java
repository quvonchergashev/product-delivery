package com.example.productdelivery.service.interfaces;

import com.example.productdelivery.dto.RegisterAdminDto;
import com.example.productdelivery.dto.UpdateStatusDto;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface AdminService {
    ResponseApi add(RegisterAdminDto registerDto, MultipartHttpServletRequest request) throws IOException;
    ResponseApi delete(Long id);
    ResponseApi updateStatus(UpdateStatusDto updateStatusDto);

    ResponseApi edit(User user);

}
