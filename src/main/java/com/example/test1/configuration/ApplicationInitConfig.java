package com.example.test1.configuration;


import com.example.test1.Constant.PredefinedRole;
import com.example.test1.Entity.Role;
import com.example.test1.Entity.User;
//import com.example.test1.enums.Role;
import com.example.test1.repository.RoleRepository;
import com.example.test1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
public class ApplicationInitConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,RoleRepository roleRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){

                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());
                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());
                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with defauld password: admin");
            }
            log.info("Application initialization completed .....");

        };
    }
}
