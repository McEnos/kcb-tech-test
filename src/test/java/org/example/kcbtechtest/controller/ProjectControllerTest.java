package org.example.kcbtechtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Project;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.example.kcbtechtest.service.ProjectService;
import org.example.kcbtechtest.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;

    private Project project;
    private List<Task> tasks;
    private ProjectDto projectDto;
    private List<TaskDto> taskDtos;

    @BeforeEach
    public void init() {
        tasks = Arrays.asList(
                Task.builder()
                        .title("title")
                        .description("description")
                        .dueDate(LocalDate.now())
                        .status(ProjectStatus.TO_DO)
                        .build(),
                Task.builder()
                        .title("title2")
                        .description("description2")
                        .dueDate(LocalDate.now())
                        .status(ProjectStatus.TO_DO)
                        .build()
        );
        project = Project.builder()
                .id(1L)
                .name("Project Name")
                .description("Project Description")
                .build();
        projectDto = ProjectDto.builder()
                .id(1L)
                .name("Project Name")
                .description("Project Description")
                .build();
        taskDtos = Arrays.asList(
                TaskDto.builder()
                        .id(1L)
                        .title("title")
                        .description("description")
                        .dueDate(LocalDate.now())
                        .status(ProjectStatus.TO_DO)
                        .build(),
                TaskDto.builder()
                        .id(2L)
                        .title("title2")
                        .description("description2")
                        .dueDate(LocalDate.now())
                        .status(ProjectStatus.TO_DO)
                        .build()
        );
    }

    @Test
    void ProjectController_createProject_ReturnsDto() throws Exception {
        given(projectService.createProject(any())).willAnswer((invocation -> invocation.getArgument(0)));

        when(projectService.createProject(any(ProjectDto.class))).thenReturn(projectDto);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(projectDto.getName()));

        verify(projectService, times(1)).createProject(any(ProjectDto.class));
    }

    @Test
    public void ProjectController_GetAllProjects_ReturnResponseDto() throws Exception {
        List<ProjectDto> responseDto = Arrays.asList(
                ProjectDto.builder()
                        .id(1L)
                        .name("Project 1")
                        .description("Project Description")
                        .build(),
                ProjectDto.builder()
                        .id(2L)
                        .name("Project 2")
                        .description("Project Description")
                        .build()
        );
        when(projectService.getProjects(PageRequest.of(0, 10))).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/projects")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[1].name").value("Project 2"));

        verify(projectService, times(1)).getProjects(PageRequest.of(0, 10));
    }

    @Test
    public void ProjectController_ProjectDetail_ReturnProjectDto() throws Exception {

        when(projectService.getProject(1L)).thenReturn(projectDto);

        mockMvc.perform(get("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(projectDto.getName()))
                .andExpect(jsonPath("$.description").value(projectDto.getDescription()));
        verify(projectService, times(1)).getProject(1L);
    }

    @Test
    void ProjectController_testAddTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .status(ProjectStatus.TO_DO)
                .title("New Task")
                .build();
        taskDto.setId(1L);
        List<TaskDto> tasks = List.of(taskDto);

        when(taskService.createTask(anyLong(), anyList())).thenReturn(true);

        mockMvc.perform(post("/api/v1/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tasks)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(true));

        verify(taskService, times(1)).createTask(eq(1L), anyList());
    }

    @Test
    void ProjectController_testGetTasks() throws Exception {
        when(taskService.getTasks(1L, PageRequest.of(0, 10))).thenReturn(taskDtos);
        mockMvc.perform(get("/api/v1/projects/1/tasks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value(taskDtos.getFirst().getTitle()))
                .andExpect(jsonPath("$[1].title").value(taskDtos.get(1).getTitle()));

        verify(taskService, times(1)).getTasks(1L, PageRequest.of(0, 10));
    }
    @Test
    void ProjectController_DeleteProject() throws Exception {
        when(projectService.deleteProject(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(1L);
    }

    @Test
    void ProjectController_testUpdateProject() throws Exception {
        ProjectDto projectDto = ProjectDto.builder()
                .name("Project 1")
                .description("Updated Project Description")
                .build();

        when(projectService.updateProject(eq(1L), any(ProjectDto.class))).thenReturn(projectDto);

        mockMvc.perform(put("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Project 1"))
                .andExpect(jsonPath("$.description").value(projectDto.getDescription()));

        verify(projectService, times(1)).updateProject(1L, projectDto);
    }

}


