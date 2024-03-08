package com.ilyasben.taskAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {
    private Long id;
    private String content;
    private boolean completed;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
