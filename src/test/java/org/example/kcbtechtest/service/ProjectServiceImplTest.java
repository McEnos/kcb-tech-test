package org.example.kcbtechtest.service;

import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.entity.Project;
import org.example.kcbtechtest.exception.ProjectNotFoundException;
import org.example.kcbtechtest.mappers.ProjectMapper;
import org.example.kcbtechtest.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .name("Test Project")
                .id(1L)
                .description("Test Description")
                .build();

        projectDto = ProjectDto.builder()
                .description("Test Description")
                .id(1L)
                .name("Test Project")
                .build();
    }

    @Test
    void ProjectService_createProject_ReturnsProjectDto() {
        when(projectMapper.toEntity(projectDto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.createProject(projectDto);

        assertNotNull(result);
        assertEquals(projectDto.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).save(project);
        verify(projectMapper, times(1)).toEntity(projectDto);
        verify(projectMapper, times(1)).toDto(project);
    }

    @Test
    void ProjectService_updateProject_ReturnsProjectDto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.updateProject(1L, projectDto);
        assertNotNull(result);
        assertEquals(projectDto.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject_ThrowsNotFoundException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject(1L, projectDto));
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void ProjectService_deleteProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        boolean result = projectService.deleteProject(1L);

        assertTrue(result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProject_ThrowsNotFoundException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(1L));
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void ProjectService_getProject_ReturnsProjectDto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.getProject(1L);

        assertNotNull(result);
        assertEquals(projectDto.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).findById(1l);
        verify(projectMapper, times(1)).toDto(project);
    }

    @Test
    void testGetProject_ThrowsNotFoundException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.getProject(1L));
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void ProjectService_getProjects_ReturnsProjectDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = Collections.singletonList(project);
        Page<Project> projectPage = new PageImpl<>(projects, pageable, 1);

        when(projectRepository.findAll(pageable)).thenReturn(projectPage);
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        List<ProjectDto> result = projectService.getProjects(pageable);

        assertEquals(1, result.size());
        assertEquals(projectDto.getDescription(), result.getFirst().getDescription());
        verify(projectRepository, times(1)).findAll(pageable);
        verify(projectMapper, times(1)).toDto(project);
    }
}