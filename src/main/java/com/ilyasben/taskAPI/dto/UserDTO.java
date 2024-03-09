package com.ilyasben.taskAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    // password was hidden for more security
    private List<TodoDTO> todoList;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}

