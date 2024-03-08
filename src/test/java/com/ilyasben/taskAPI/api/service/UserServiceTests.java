package com.ilyasben.taskAPI.api.service;

import com.ilyasben.taskAPI.dto.UserDTO;
import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.exception.UsernameAlreadyExistsException;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.request.CreateUserRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_getUsers_ReturnsListOfUsersDTO() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = new ArrayList<>();
        userDTOs.add(new UserDTO());
        userDTOs.add(new UserDTO());
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

        // Act
        List<UserDTO> result = userService.getUsers();

        // Assert
        assertNotNull(result);
        assertEquals(users.size(), result.size());
    }

    @Test
    public void UserService_getUserById_ReturnsUserDTO_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void UserService_getUserById_ThrowsUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    public void UserService_deleteUser_DeletesUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void UserService_deleteUser_ThrowsUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }
}
