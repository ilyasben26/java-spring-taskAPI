package com.ilyasben.taskAPI.repository;

import com.ilyasben.taskAPI.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
}
