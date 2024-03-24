package com.hiro.twitterspringsecurity.configs;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hiro.twitterspringsecurity.entities.Role;
import com.hiro.twitterspringsecurity.entities.User;
import com.hiro.twitterspringsecurity.repositories.RoleRepository;
import com.hiro.twitterspringsecurity.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig  implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
            (user) -> {
                System.out.println("Admin já existe");
            },
            
            () -> {
                var user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRoles(Set.of(roleAdmin));
                
                userRepository.save(user);
            }
        );
    }
    
}