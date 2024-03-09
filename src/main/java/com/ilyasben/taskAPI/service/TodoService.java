package com.ilyasben.taskAPI.service;

import com.ilyasben.taskAPI.dto.TodoDTO;
import com.ilyasben.taskAPI.model.Todo;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    // dependency injection
    private TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

}
