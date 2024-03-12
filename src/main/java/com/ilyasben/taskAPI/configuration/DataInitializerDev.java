package com.ilyasben.taskAPI.configuration;
import com.ilyasben.taskAPI.model.User;
import com.ilyasben.taskAPI.repository.TodoRepository;
import com.ilyasben.taskAPI.repository.UserRepository;
import com.ilyasben.taskAPI.request.CreateTodoRequest;
import com.ilyasben.taskAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Run only in development profile
public class DataInitializerDev implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        // putting sample data in the db
        //User user = new User();
        //user.setPassword("should be hashed later on :)");
        //user.setUsername("Ilyas");
        //User savedUser = userRepository.save(user);
        //userService.createTodoForUser(savedUser.getId(), new CreateTodoRequest("sample task"));

    }
}
