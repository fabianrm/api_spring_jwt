package com.hampcode.service;

import com.hampcode.dto.TaskReport;
import com.hampcode.dto.TaskRequest;
import com.hampcode.dto.TaskResponse;
import com.hampcode.model.TaskPriority;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    TaskResponse updateTask(Long id, TaskRequest request);
    void deleteTask(Long id);
    TaskResponse getTaskById(Long id);
    List<TaskResponse> getAllTasks();

    List<TaskResponse> findTasksByDateRange(LocalDate startDate, LocalDate endDate);
    List<TaskResponse> findTasksByPriority(TaskPriority priority);

    void markTaskAsCompleted(Long id);

    List<TaskReport> getTaskReport();

    List<TaskResponse> findTasksByProjectId(Long projectId);
    List<TaskResponse> findTasksByProjectName(String projectName);

}
