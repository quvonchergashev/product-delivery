package com.example.productdelivery.component;

import com.example.productdelivery.entity.Permission;
import com.example.productdelivery.entity.Roles;
import com.example.productdelivery.entity.User;
import com.example.productdelivery.repositories.PermissionRepository;
import com.example.productdelivery.repositories.RoleRepository;
import com.example.productdelivery.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private String initialMode="always";
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("")) {

            List<Permission> permissions=new ArrayList<>();
            Permission permission=new Permission();
            permission.setName("ADD_ADMIN");
            Permission permission1 = permissionRepository.save(permission);
            permissions.add(permission1);

            Roles roles=new Roles();
            roles.setPermissions(permissions);
            roles.setName("ROLE_SUPER_ADMIN");
            roleRepository.save(roles);
            Roles roles1=new Roles();
            roles1.setName("ROLE_ADMIN");
            roleRepository.save(roles1);
            Roles roles2=new Roles();
            roles1.setName("ROLE_USER");
            roleRepository.save(roles2);


            User user1 = new User();
            user1.setPassword(passwordEncoder.encode("BekBola1999"));
            user1.setRole(roles);
            user1.setEmail("ergashevq346@gmail.com");
            user1.setFirstname("Quvonchbek");
            user1.setLastname("Ergashev");
            user1.setUsername("bekbola");
            user1.setPhoneNumber("+998909008870");
            user1.setImage("D:\\photo_2023-01-11_03-30-13.jpg");
            User user = userRepository.save(user1);
        }
    }
}
