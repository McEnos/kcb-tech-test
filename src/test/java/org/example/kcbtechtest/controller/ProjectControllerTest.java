package org.example.kcbtechtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Project;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.example.kcbtechtest.service.ProjectService;
import org.example.kcbtechtest.service.TaskService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    public void init(){
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
    }
    @Test
    void ProjectController_createProject_ReturnsDto() throws Exception {
        given(projectService.createProject(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(projectDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(projectDto.getDescription())));
    }
    @Test
    public void ProjectController_GetAllProjects_ReturnResponseDto() throws Exception {
        List<ProjectDto> responseDto = Arrays.asList(
                ProjectDto.builder()
                        .id(1L)
                        .name("Project Name")
                        .description("Project Description")
                        .build(),
                ProjectDto.builder()
                        .id(2L)
                        .name("Project Name")
                        .description("Project Description")
                        .build()
        );
        when(projectService.getProjects(PageRequest.of(0,10))).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void  ProjectController_ProjectDetail_ReturnProjectDto() throws Exception {

        when(projectService.getProject(1L)).thenReturn(projectDto);

        ResultActions response = mockMvc.perform(get("/api/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(projectDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(projectDto.getDescription())));
    }

    @Test
    public void ProjectController_DeleteProject_ReturnString() throws Exception {
        int pokemonId = 1;
        doNothing().when(projectService).deleteProject(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}


