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
public class UserRepositoryTest {
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
}
