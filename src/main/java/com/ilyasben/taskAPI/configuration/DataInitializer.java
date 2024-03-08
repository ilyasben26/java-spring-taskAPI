package com.ilyasben.taskAPI.configuration;
import com.ilyasben.taskAPI.model.Todo;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Run only in development profile
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

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
