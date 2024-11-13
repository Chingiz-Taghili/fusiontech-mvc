package com.multishop.fusiontech.config;

import com.multishop.fusiontech.enums.Gender;
import com.multishop.fusiontech.models.Role;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.RoleRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class AdminAccountInitializer {

    @Bean
    CommandLineRunner initAdminAccount(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = RoleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            if (userRepository.findByEmail("admin@mail.com") == null) {
                UserEntity admin = new UserEntity();
                admin.setName("Admin");
                admin.setSurname("Adminzadə");
                admin.setGender(Gender.MALE);
                admin.setImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png");
                admin.setEmail("admin@mail.com");
                admin.setPassword(passwordEncoder.encode("12345"));
                admin.setRoles(Collections.singletonList(adminRole));
                userRepository.save(admin);
            }
        };
    }
}
