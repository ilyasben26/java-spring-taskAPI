package com.ilyasben.taskAPI.service;

import com.ilyasben.taskAPI.dto.UserDTO;
import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.exception.UsernameAlreadyExistsException;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.request.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Only allow the admin to get and see all users ...
@Service
public class UserService {
    // dependency injections
    private UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getUsers() {
        // fetching and converting the users to DTO
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO addUser(CreateUserRequest createUserRequest) {
        // TODO: check for DB constraint violations
        // checking if the username already exists
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        } else {
            User user = modelMapper.map(createUserRequest, User.class);
            User savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UserDTO.class);
        }

    }

    public UserDTO updateUser(Long userId, CreateUserRequest updatedUserInfo) {
        // Check if the username already exists
        if (userRepository.existsByUsernameAndIdNot(updatedUserInfo.getUsername(), userId)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setUsername(updatedUserInfo.getUsername());
        existingUser.setPassword(updatedUserInfo.getPassword());

        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public void deleteUser(Long userId) {
        // checking if the user exists
        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


}
