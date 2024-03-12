package com.ilyasben.taskAPI.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todos")
@SecurityRequirement(name = "bearerAuth")
public class TodoController {

}
