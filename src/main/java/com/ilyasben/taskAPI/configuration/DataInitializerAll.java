package com.ilyasben.taskAPI.configuration;

import com.ilyasben.taskAPI.model.Role;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.RoleRepository;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DataInitializerAll implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    return roleRepository.save(role);
                });

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    return roleRepository.save(role);
                });

        // adding an admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password")); // encoding the password
        admin.setRoles(Collections.singletonList(adminRole));
        userRepository.save(admin);


        // adding a normal user
        User normalUser = new User();
        normalUser.setUsername("ilyas");
        normalUser.setPassword(passwordEncoder.encode("password")); // encoding the password
        normalUser.setRoles(Collections.singletonList(userRole));
        userRepository.save(normalUser);



    }
}
