package com.ilyasben.taskAPI.configuration;

import com.ilyasben.taskAPI.model.Role;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.RoleRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializerAll implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // creating a USER role
        Role roleUser = new Role();
        roleUser.setName("USER");
        roleRepository.save(roleUser);

        // creating an ADMIN role
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleAdmin);

        // adding an admin
        User admin = new User();
        admin.setUsername("Admin");
        admin.setPassword(passwordEncoder.encode("password")); // encoding the password

        userRepository.save(admin);
    }
}
