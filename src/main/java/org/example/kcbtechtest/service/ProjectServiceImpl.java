package org.example.kcbtechtest.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.entity.Project;
import org.example.kcbtechtest.exception.ProjectNotFoundException;
import org.example.kcbtechtest.mappers.ProjectMapper;
import org.example.kcbtechtest.repository.ProjectRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project entity = projectMapper.toEntity(projectDto);
        return projectMapper.toDto(projectRepository.save(entity));
    }

    @Override
    public ProjectDto updateProject(Long id,ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getId()).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        projectRepository.delete(project);
        return true;

    }

    @Override
    public ProjectDto getProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }
        return projectMapper.toDto(project.get());
    }

    @Override
    public List<ProjectDto> getProjects(Pageable pageable) {
        List<ProjectDto> result = new ArrayList<>(pageable.getPageSize());
        projectRepository.findAll(pageable).forEach(p -> result.add(projectMapper.toDto(p)));
        return result;
    }
}
