package org.example.kcbtechtest.service;

import org.example.kcbtechtest.dto.ProjectDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(Long id,ProjectDto projectDto);

    boolean deleteProject(Long id);

    ProjectDto getProject(Long id);

    List<ProjectDto> getProjects(Pageable pageable);
}
