package com.ilyasben.taskAPI.api.controller;

import com.ilyasben.taskAPI.dto.TodoDTO;
import com.ilyasben.taskAPI.dto.UserDTO;
import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.exception.UsernameAlreadyExistsException;
import com.ilyasben.taskAPI.request.CreateUserRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void UserController_getUsers_ReturnsListOfUserDTO() throws Exception {
        // Arrange
        List<UserDTO> userDTOList = new ArrayList<>();
        when(userService.getUsers()).thenReturn(userDTOList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void UserController_getUserById_ReturnsUserDTO_WhenUserExists() throws Exception {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.getUserById(userId)).thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void UserController_getUserById_ReturnsNotFound_WhenUserDoesNotExist() throws Exception {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenThrow(UserNotFoundException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void UserController_addUser_ReturnsOk_WhenUserIsAdded() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        when(userService.addUser(request)).thenReturn(new UserDTO());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"password\"}"))
                .andExpect(status().isOk());
    }

    // TODO: fix this test case
    @Test
    public void UserController_addUser_ReturnsConflict_WhenUsernameAlreadyExists() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        when(userService.addUser(request)).thenThrow(UsernameAlreadyExistsException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"existingUsername\", \"password\": \"password\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void UserController_deleteUser_ReturnsOk_WhenUserIsDeleted() throws Exception {
        // Arrange
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void UserController_deleteUser_ReturnsNotFound_WhenUserDoesNotExist() throws Exception {
        // Arrange
        Long userId = 1L;
        doThrow(UserNotFoundException.class).when(userService).deleteUser(userId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // todo: add tests for adding a task and getting all task for a user
}
