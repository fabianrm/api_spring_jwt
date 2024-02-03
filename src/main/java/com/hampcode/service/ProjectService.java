package com.hampcode.service;

import com.hampcode.dto.ProjectRequest;
import com.hampcode.dto.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    ProjectResponse updateProject(Long id, ProjectRequest request);
    void deleteProject(Long id);
    ProjectResponse getProjectById(Long id);
    List<ProjectResponse> getAllProjects();

    List<ProjectResponse> findProjectsByNameContaining(String name);


}
