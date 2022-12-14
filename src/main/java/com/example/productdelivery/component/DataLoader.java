package com.example.productdelivery.component;

import com.example.productdelivery.consts.RoleName;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.repositories.RoleRepository;
import com.example.productdelivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private String initialMode="always";


    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Roles admin = new Roles();
            admin.setRoleName(RoleName.ROLE_ADMIN);
            Roles user = new Roles();
            user.setRoleName(RoleName.ROLE_USER);
            Roles save = roleRepository.save(admin);
            Roles save1 = roleRepository.save(user);
            List<Roles> roles = new ArrayList<>();
            roles.add(save);
            roles.add(save1);


            User user1 = new User();
            user1.setPassword(passwordEncoder.encode("12345"));
            user1.setRoles(roles);
            user1.setPhoneNumber("909008870");
            user1.setFullName("Quvonchbek");
            userRepository.save(user1);

        }

    }
}
