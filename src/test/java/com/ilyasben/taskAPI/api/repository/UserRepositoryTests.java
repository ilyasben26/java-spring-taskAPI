package com.ilyasben.taskAPI.api.repository;

import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    // Only testing the repository methods that are actually used by the API

    @Test
    public void UserRepository_Save_ReturnsSavedUser() {

        // Arrange
        User user = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAll_ReturnsMoreThanOneUser() {

        // Arrange
        User user1 = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();
        User user2 = User.builder()
                .username("test_user02")
                .password("test_password")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        List<User> userList = userRepository.findAll();

        // Assert
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_FindById_ReturnsUser() {
        // Arrange
        User user = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();

        userRepository.save(user);


        // Act
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(user.getId()).isEqualTo(foundUser.getId());
    }

    @Test
    public void UserRepository_Delete_UserIsDeleted() {
        // Arrange
        User user = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();
        userRepository.save(user);
        User userFound = userRepository.findById(user.getId()).get();

        // Act
        userRepository.delete(userFound);

        // Assert
        assertThat(userRepository.findById(userFound.getId()).isPresent()).isFalse();
    }

    @Test
    public void UserRepository_existsByUsername_ReturnsTrue() {
        // Arrange
        User user = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();
        userRepository.save(user);

        // Act & Assert
        assertThat(userRepository.existsByUsername(user.getUsername())).isTrue();
    }

    @Test
    public void UserRepository_existsByUsername_ReturnsFalse() {
        // Arrange
        User user = User.builder()
                .username("test_user01")
                .password("test_password")
                .build();
        userRepository.save(user);

        // Act & Assert
        assertThat(userRepository.existsByUsername("test_user01111")).isFalse();
    }
}
