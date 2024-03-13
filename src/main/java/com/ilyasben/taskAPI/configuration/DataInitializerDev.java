package com.ilyasben.taskAPI.configuration;
import com.ilyasben.taskAPI.model.Role;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.RoleRepository;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("dev") // Run only in development profile
public class DataInitializerDev implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {


    }
}
