# Todo API
A JWT secured API for managing Todos made using Spring Boot.
## What I learned from making this API
- The basics of how to make an API with Spring Boot.
- Dependency Injection.
- Usage of N-Layers Architecture (Controller <-> Service <-> Repository <-> DB)
- Usage of DTO and Request patterns.
- Using Swagger/OpenAPI to document the API.
- Using JUnit and Mockito to test the API.
- Using Spring Security to secure the API with:
  - JWT Filter
  - Authentication
  - Request Matchers
  - Roles
- Using AppMap to map and explore the API and generate flow diagrams.
## How to run
- Make sure you are using Java 21 with `java --version`.
- Download the JAR file from the releases.
- Make sure port 8080 is not being used.
- Run the JAR file with `java -jar taskAPI-0.0.1-SNAPSHOT.jar`.
- Go to `http://localhost:8080/swagger-ui/index.html`.
- Use the `/api/v1/auth/login` to login as an admin with:
  - username: admin
  - password: password
- Copy the returned token. 
- Click on Authorize and paste the copied token there.
- Try making some requests.
# User Stories
I managed to implement the following user stories.
### Normal User
As a default user I should be able to:
- Register with a username and password.
- Login using my username and password.
- Create todos for myself only.
- Edit my todos.
- Toggle my todos.
- Delete my todos.
### Admin
As an admin user I should be able to:
- Register other users.
- Login using the "admin" username and a preset password.
- See all users.
- Edit other users.
- Create todos for other users.
- See all the todos of other users.
- Edit all the todos of other users.
- Toggle all the todos of other users.
- Delete all the todos of other users.
# Flow Diagrams
## Login
![appMapLoginSuccess](https://github.com/ilyasben26/java-spring-taskAPI/assets/73348981/f5d00bff-e786-4388-92e2-e653e3ea501c)
## Add Todo
![appMapAddTodo](https://github.com/ilyasben26/java-spring-taskAPI/assets/73348981/0f654380-266c-4382-aa23-39aa4acbe810)
