package com.hampcode.service;

import com.hampcode.dto.ProjectAssignmentRequest;
import com.hampcode.dto.ProjectAssignmentResponse;

public interface ProjectAssignmentService {
    ProjectAssignmentResponse createAssignment(ProjectAssignmentRequest request);

}
