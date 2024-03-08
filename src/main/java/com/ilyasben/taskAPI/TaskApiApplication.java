package com.ilyasben.taskAPI;

import com.ilyasben.taskAPI.model.Todo;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TodoRepository todoRepository;


	public static void main(String[] args) {
		SpringApplication.run(TaskApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// putting sample data in the db
		User user = new User();
		user.setPassword("should be hashed later on :)");
		user.setUsername("Ilyas");

		Todo todo = new Todo();
		todo.setContent("Update CV");

		user.getTodoList().add(todo);

		todoRepository.save(todo);
		userRepository.save(user);

	}
}
