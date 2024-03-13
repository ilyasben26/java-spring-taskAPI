package com.ilyasben.taskAPI.controller;


import com.ilyasben.taskAPI.dto.TodoDTO;
import com.ilyasben.taskAPI.dto.UserDTO;

import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.exception.UsernameAlreadyExistsException;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import com.ilyasben.taskAPI.request.CreateUserRequest;
import com.ilyasben.taskAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    // Dependency injections
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all todos for a user."
    )
    @GetMapping("/{userId}/todos")
    public ResponseEntity<?> getAllTodosForUser(@PathVariable Long userId, Authentication authentication) {
        try {
            // fetch requested username
            String reqUsername = userService.getUserById(userId).getUsername();

            // Only allow Admin or the user himself to see the todos
            if (hasPermission(authentication, reqUsername)) {
                List<TodoDTO> todos = userService.getTodosForUser(userId);
                return new ResponseEntity<>(todos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access to todos", HttpStatus.UNAUTHORIZED);
            }
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Add a todo for a user."
    )
    @PostMapping("/{userId}/todo")
    public ResponseEntity<?> createTodoForUser(@PathVariable Long userId, @RequestBody CreateTodoRequest createTodoRequest, Authentication authentication) {
        try {
            String reqUsername = userService.getUserById(userId).getUsername();

            if (hasPermission(authentication, reqUsername)) {
                userService.createTodoForUser(userId, createTodoRequest);
                return new ResponseEntity<>("To-Do added successfully to user.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access to create todo", HttpStatus.UNAUTHORIZED);
            }
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Toggle a todo for a user."
    )
    @PutMapping("/{userId}/todo/{todoId}/toggle")
    public ResponseEntity<?> toggleTodoForUser(@PathVariable Long userId, @PathVariable Long todoId, Authentication authentication) {
        try {
            String reqUsername = userService.getUserById(userId).getUsername();

            if (hasPermission(authentication, reqUsername)) {
                TodoDTO todoDTO = userService.toggleTodoForUser(userId, todoId);
                return new ResponseEntity<>(todoDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access to toggle todo", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Modify a todo for a user."
    )
    @PutMapping("/{userId}/todo/{todoId}")
    public ResponseEntity<?> updateTodoForUser(@PathVariable Long userId, @PathVariable Long todoId, @RequestBody CreateTodoRequest createTodoRequest, Authentication authentication) {
        try {
            String reqUsername = userService.getUserById(userId).getUsername();

            if (hasPermission(authentication, reqUsername)) {
                TodoDTO todoDTO = userService.updateTodoForUser(userId, todoId, createTodoRequest);
                return new ResponseEntity<>(todoDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access to update todo", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Delete a todo for a user."
    )
    @DeleteMapping("/{userId}/todo/{todoId}")
    public ResponseEntity<?> deleteTodoForUser(@PathVariable Long userId, @PathVariable Long todoId, Authentication authentication) {
        try {
            String reqUsername = userService.getUserById(userId).getUsername();

            if (hasPermission(authentication, reqUsername)) {
                userService.deleteTodoForUser(userId, todoId);
                return new ResponseEntity<>("Todo deleted successfully for user", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access to delete todo", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Utility
    private boolean hasPermission(Authentication authentication, String reqUsername) {
        String username = authentication.getName();
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
                username.equals(reqUsername);
    }
}
