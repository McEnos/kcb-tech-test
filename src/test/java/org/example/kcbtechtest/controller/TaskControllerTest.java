package org.example.kcbtechtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.example.kcbtechtest.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {
    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;
    private TaskDto taskDto;
    @Autowired
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .description("description")
                .dueDate(LocalDate.now())
                .title("title")
                .status(ProjectStatus.TO_DO)
                .build();
        taskDto = TaskDto.builder()
                .id(1L)
                .description("description")
                .dueDate(LocalDate.now())
                .title("title")
                .status(ProjectStatus.TO_DO)
                .build();
    }


    @Test
    void TaskController_updateTask() throws Exception {
        TaskDto dto = TaskDto.builder()
                .title("Updated title")
                .description("Updated task Description")
                .dueDate(LocalDate.now().minusDays(1))
                .status(ProjectStatus.TO_DO)
                .build();
        when(taskService.updateTask(1L, dto)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()));

        verify(taskService, times(1)).updateTask(1L, dto);
    }

    @Test
    void TaskController_deleteTask() throws Exception {
        when(taskService.deleteTask(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
        verify(taskService, times(1)).deleteTask(anyLong());
    }

    @Test
    void TaskController_getTasks() throws Exception {
        List<TaskDto> taskDtos = List.of(taskDto);
        String status = ProjectStatus.TO_DO.name();
        LocalDate date = LocalDate.now();
        when(taskService.filterTasks(status, date)).thenReturn(taskDtos);

        mockMvc.perform(get("/api/v1/tasks")
                        .param("status", status)
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].dueDate").value(LocalDate.now().toString()));
        verify(taskService, times(1)).filterTasks(status, date);
    }
}