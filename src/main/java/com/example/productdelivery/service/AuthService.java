package com.example.productdelivery.service;

import com.example.productdelivery.dto.LoginDto;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.repositories.UserRepository;
import com.example.productdelivery.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByPhoneNumber(phoneNumber);
        if (byUsername.isPresent())
            return byUsername.get();
        throw new UsernameNotFoundException("User topilmadi");
    }

    public ResponseApi login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getPhoneNumber(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getPhoneNumber());
            return new ResponseApi(token, true);
        } catch (BadCredentialsException e) {
            return new ResponseApi("Parol yoki telefon nomer xato", false);
        }
    }



}
