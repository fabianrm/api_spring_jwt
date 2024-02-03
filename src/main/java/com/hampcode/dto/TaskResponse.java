package com.hampcode.dto;


import com.hampcode.model.TaskPriority;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//TaskResponse sería para devolver información de las tareas.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private TaskPriority priority;
    private String projectName;
}