package com.example.productdelivery.service;
import com.example.productdelivery.config.UserDetailsImpl;
import com.example.productdelivery.entity.Users;
import com.example.productdelivery.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> userOpt = userRepository.findByUserName(userName);
        Users user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Not found" + userName));
        log.info("loadUserByUsername() : {}", user);
        return new UserDetailsImpl(user);
    }
}
