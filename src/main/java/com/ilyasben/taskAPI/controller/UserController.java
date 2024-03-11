package com.ilyasben.taskAPI.controller;


import com.ilyasben.taskAPI.dto.TodoDTO;
import com.ilyasben.taskAPI.dto.UserDTO;

import com.ilyasben.taskAPI.exception.UserNotFoundException;
import com.ilyasben.taskAPI.exception.UsernameAlreadyExistsException;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import com.ilyasben.taskAPI.request.CreateUserRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // Dependency injections
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody CreateUserRequest createUserRequest) {
        try {
            UserDTO userDTO = userService.updateUser(userId, createUserRequest);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UsernameAlreadyExistsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/todos")
    public ResponseEntity<?> getAllTodosForUser(@PathVariable Long userId) {
        try {
            List<TodoDTO> todos = userService.getTodosForUser(userId);
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}/todo")
    public ResponseEntity<?> createTodoForUser(@PathVariable Long userId, @RequestBody CreateTodoRequest createTodoRequest) {
        try {
            userService.createTodoForUser(userId, createTodoRequest);
            return new ResponseEntity<>("To-Do added successfully to user.", HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}/todo/{todoId}/toggle")
    public ResponseEntity<?> toggleTodoForUser(@PathVariable Long userId, @PathVariable Long todoId) {
        try {
            TodoDTO todoDTO = userService.toggleTodoForUser(userId, todoId);
            return new ResponseEntity<>(todoDTO, HttpStatus.OK);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}/todo/{todoId}")
    public ResponseEntity<?> updateTodoForUser(@PathVariable Long userId, @PathVariable Long todoId, @RequestBody CreateTodoRequest createTodoRequest) {
        try {
            TodoDTO todoDTO = userService.updateTodoForUser(userId, todoId, createTodoRequest);
            return new ResponseEntity<>(todoDTO, HttpStatus.OK);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /*

    @PostMapping("/{userId}/todos")
    public void addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest) {
        // TODO: find out how to use a DTO instead of todoRequest
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Todo todo = new Todo();
        todo.setContent(todoRequest.getContent());
        user.getTodoList().add(todo);
        todoRepository.save(todo);
        userRepository.save(user);
    }

    @PostMapping("/todos/{todoId}")
    public void toggleTodoCompleted(@PathVariable Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    @DeleteMapping("/todos/{todoId}")
    @Transactional
    public void deleteTodo(@PathVariable Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
        todoRepository.delete(todo);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        userRepository.delete(user);
    }

     */
}
