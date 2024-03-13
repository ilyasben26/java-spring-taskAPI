package com.ilyasben.taskAPI.api.controller;

import com.ilyasben.taskAPI.controller.UserController;
import com.ilyasben.taskAPI.dto.TodoDTO;
import com.ilyasben.taskAPI.dto.UserDTO;
import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodosForUser_WithValidUser_ReturnsTodos() {
        // Arrange
        Long userId = 1L;
        String reqUsername = "testUser";

        UserDTO fetchedUser = new UserDTO();
        fetchedUser.setId(userId);
        fetchedUser.setUsername(reqUsername);

        Authentication authentication = mock(Authentication.class);
        when(userService.getUserById(userId)).thenReturn(fetchedUser);
        when(userService.getTodosForUser(userId)).thenReturn(Collections.singletonList(new TodoDTO()));
        when(authentication.getName()).thenReturn(reqUsername);

        // Act
        ResponseEntity<?> response = userController.getAllTodosForUser(userId, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllTodosForUser_WithUnauthorizedUser_ReturnsUnauthorized() {
        // Arrange
        Long userId = 2L;
        String reqUsername = "otherUser";
        UserDTO fetchedUser = new UserDTO();
        fetchedUser.setId(userId);
        fetchedUser.setUsername(reqUsername);

        Authentication authentication = mock(Authentication.class);

        when(userService.getUserById(userId)).thenReturn(fetchedUser); // the one we are requesting
        when(authentication.getName()).thenReturn("differentUser"); // the one making the request is another user

        // Act
        ResponseEntity<?> response = userController.getAllTodosForUser(userId, authentication);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized access to todos", response.getBody());
    }

    // When an admin makes the request
    @Test
    void testGetAllTodosForUser_WithAdminUser_ReturnsTodos() {
        // Arrange
        Long userId = 2L;
        String reqUsername = "otherUser";
        UserDTO fetchedUser = new UserDTO();
        fetchedUser.setId(userId);
        fetchedUser.setUsername(reqUsername);

        Authentication authentication = mock(Authentication.class);
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ADMIN");
        Collection<SimpleGrantedAuthority> authCollection = Collections.singleton(simpleGrantedAuthority);
        when(authentication.getName()).thenReturn("admin");
        when(authentication.getAuthorities()).thenReturn((Collection) authCollection);

        when(userService.getUserById(userId)).thenReturn(fetchedUser);
        when(userService.getTodosForUser(userId)).thenReturn(Collections.singletonList(new TodoDTO()));

        // Act
        ResponseEntity<?> response = userController.getAllTodosForUser(userId, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateTodoForUser_WithValidUser_CreatesTodo() {
        // Arrange
        Long userId = 1L;
        String reqUsername = "testUser";
        UserDTO fetchedUser = new UserDTO();
        fetchedUser.setId(userId);
        fetchedUser.setUsername(reqUsername);

        CreateTodoRequest createTodoRequest = new CreateTodoRequest();


        Authentication authentication = mock(Authentication.class);
        when(userService.getUserById(userId)).thenReturn(fetchedUser);
        when(userService.createTodoForUser(userId, createTodoRequest)).thenReturn(true);
        when(authentication.getName()).thenReturn(reqUsername);

        // Act
        ResponseEntity<?> response = userController.createTodoForUser(userId, createTodoRequest, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("To-Do added successfully to user.", response.getBody());
    }

    @Test
    void testToggleTodoForUser_WithValidUser_TogglesTodo() {
        // Arrange
        Long userId = 1L;
        String reqUsername = "testUser";
        UserDTO fetchedUser = new UserDTO();
        fetchedUser.setId(userId);
        fetchedUser.setUsername(reqUsername);

        Long todoId = 1L;
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todoId);
        todoDTO.setCompleted(false);

        TodoDTO toggeledTodoDTO = new TodoDTO();
        toggeledTodoDTO.setId(todoId);
        toggeledTodoDTO.setCompleted(true);

        Authentication authentication = mock(Authentication.class);
        when(userService.getUserById(userId)).thenReturn(fetchedUser);
        when(userService.toggleTodoForUser(userId, todoId)).thenReturn(toggeledTodoDTO);
        when(authentication.getName()).thenReturn(reqUsername);

        // Act
        ResponseEntity<?> response = userController.toggleTodoForUser(userId, todoId, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toggeledTodoDTO, response.getBody());

    }

}
