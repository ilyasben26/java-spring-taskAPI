package com.ilyasben.taskAPI.repository;

import com.ilyasben.taskAPI.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
