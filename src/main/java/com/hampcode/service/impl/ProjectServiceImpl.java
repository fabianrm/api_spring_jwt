package com.hampcode.service.impl;

import com.hampcode.dto.ProjectRequest;
import com.hampcode.dto.ProjectResponse;
import com.hampcode.exception.ResourceNotFoundException;
import com.hampcode.mapper.ProjectMapper;
import com.hampcode.model.Project;
import com.hampcode.model.User;
import com.hampcode.repository.ProjectRepository;
import com.hampcode.repository.UserRepository;
import com.hampcode.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository,
                              ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.projectRequestToProject(request);

        return getProjectResponse(request, project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                //.orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        project.setName(request.getName());

        return getProjectResponse(request, project);
    }



    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            //throw new RuntimeException("Project not found with id: " + id);
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                //.orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return projectMapper.projectToProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(projectMapper::projectToProjectResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> findProjectsByNameContaining(String name) {
        List<ProjectResponse> projectResponses = projectRepository.findByNameContaining(name)
                .stream()
                .map(projectMapper::projectToProjectResponse)
                .collect(Collectors.toList());
        return projectResponses;
    }

    private ProjectResponse getProjectResponse(ProjectRequest request, Project project) {
        Optional<Long> userIdOptional = Optional.ofNullable(request.getUserId());
        if (userIdOptional.isPresent()) {
            User newUser = userRepository.findById(userIdOptional.get())
                    //.orElseThrow(() -> new RuntimeException("User not found with id: " + userIdOptional.get()));
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userIdOptional.get()));
            project.setUser(newUser);
        }

        project = projectRepository.save(project);
        return projectMapper.projectToProjectResponse(project);
    }

    private void updateProjectFromRequest(Project project, ProjectRequest request) {
        project.setName(request.getName());
        // Repite para otros campos si los hay
    }
}
