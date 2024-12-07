package org.example.kcbtechtest.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.example.kcbtechtest.dto.ProjectDto;

import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.service.ProjectService;
import org.example.kcbtechtest.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProjectController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProjectControllerDiffblueTest {
    @Autowired
    private ProjectController projectController;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private TaskService taskService;

    /**
     * Test {@link ProjectController#addTask(Long, List)}.
     * <p>
     * Method under test: {@link ProjectController#addTask(Long, List)}
     */
    @Test
    @DisplayName("Test addTask(Long, List)")
    void testAddTask() throws Exception {
        // Arrange
        when(taskService.createTask(Mockito.<Long>any(), Mockito.<List<TaskDto>>any())).thenReturn(true);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/api/v1/projects/{projectId}/tasks", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder);

        // Assert
        ResultActions resultActions = actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Test {@link ProjectController#createProject(ProjectDto)}.
     * <p>
     * Method under test: {@link ProjectController#createProject(ProjectDto)}
     */
    @Test
    @DisplayName("Test createProject(ProjectDto)")
    void testCreateProject() throws Exception {
        // Arrange
        ProjectDto buildResult = ProjectDto.builder()
                .description("The characteristics of someone or something")
                .id(1L)
                .name("Name")
                .build();
        when(projectService.createProject(Mockito.any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        ProjectDto buildResult2 = ProjectDto.builder()
                .description("The characteristics of someone or something")
                .id(1L)
                .name("Name")
                .build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult2));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\"}"));
    }

    /**
     * Test {@link ProjectController#getProjects(Integer, Integer)}.
     * <p>
     * Method under test: {@link ProjectController#getProjects(Integer, Integer)}
     */
    @Test
    @DisplayName("Test getProjects(Integer, Integer)")
    void testGetProjects() throws Exception {
        // Arrange
        when(projectService.getProjects(Mockito.<Pageable>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/projects");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link ProjectController#getProject(Long)}.
     * <p>
     * Method under test: {@link ProjectController#getProject(Long)}
     */
    @Test
    @DisplayName("Test getProject(Long)")
    void testGetProject() throws Exception {
        // Arrange
        ProjectDto buildResult = ProjectDto.builder()
                .description("The characteristics of someone or something")
                .id(1L)
                .name("Name")
                .build();
        when(projectService.getProject(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/projects/{projectId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\"}"));
    }

    /**
     * Test {@link ProjectController#getTasks(Long, Integer, Integer)}.
     * <p>
     * Method under test: {@link ProjectController#getTasks(Long, Integer, Integer)}
     */
    @Test
    @DisplayName("Test getTasks(Long, Integer, Integer)")
    void testGetTasks() throws Exception {
        // Arrange
        when(taskService.getTasks(Mockito.<Long>any(), Mockito.<Pageable>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/projects/{projectId}/tasks", 1L);
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link ProjectController#deleteProject(Long)}.
     * <p>
     * Method under test: {@link ProjectController#deleteProject(Long)}
     */
    @Test
    @DisplayName("Test deleteProject(Long)")
    void testDeleteProject() throws Exception {
        // Arrange
        when(projectService.deleteProject(Mockito.<Long>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/projects/{projectId}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder);

        // Assert
        ResultActions resultActions = actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Test {@link ProjectController#updateTask(Long, ProjectDto)}.
     * <p>
     * Method under test: {@link ProjectController#updateTask(Long, ProjectDto)}
     */
    @Test
    @DisplayName("Test updateTask(Long, ProjectDto)")
    void testUpdateTask() throws Exception {
        // Arrange
        ProjectDto buildResult = ProjectDto.builder()
                .description("The characteristics of someone or something")
                .id(1L)
                .name("Name")
                .build();
        when(projectService.updateProject(Mockito.<Long>any(), Mockito.<ProjectDto>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/v1/projects/{projectId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        ProjectDto buildResult2 = ProjectDto.builder()
                .description("The characteristics of someone or something")
                .id(1L)
                .name("Name")
                .build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult2));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(projectController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\"}"));
    }
}
