package com.ilyasben.taskAPI.controller;

import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
