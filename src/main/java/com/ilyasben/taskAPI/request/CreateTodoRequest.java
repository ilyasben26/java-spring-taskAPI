package com.ilyasben.taskAPI.request;

import com.ilyasben.taskAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {
    private String content;
}
