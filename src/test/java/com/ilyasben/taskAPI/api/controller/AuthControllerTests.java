package com.ilyasben.taskAPI.api.controller;
import com.ilyasben.taskAPI.controller.AuthController;
import com.ilyasben.taskAPI.dto.AuthResponseDTO;
import com.ilyasben.taskAPI.dto.LoginDTO;
import com.ilyasben.taskAPI.dto.RegisterDTO;
import com.ilyasben.taskAPI.model.Role;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.RoleRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.security.JWTGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator tokenGenerator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenGenerator.generateToken(authentication)).thenReturn("token");

        ResponseEntity<AuthResponseDTO> response = authController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody().getAccessToken());
    }

    @Test
    void testRegister() {
        RegisterDTO registerDTO = new RegisterDTO("username", "password");
        when(userRepository.existsByUsername("username")).thenReturn(false);

        Role role = new Role();
        role.setName("USER");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));

        ResponseEntity<String> response = authController.register(registerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }
}
