package org.example.kcbtechtest.service;

import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.example.kcbtechtest.exception.TaskNotFoundException;
import org.example.kcbtechtest.mappers.TaskMapper;
import org.example.kcbtechtest.repository.TaskRepository;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .status(ProjectStatus.TO_DO)
                .dueDate(LocalDate.now())
                .title("Test task")
                .description("Test description")
                .id(1L)
                .build();
        taskDto = TaskDto.builder()
                .title("Test task")
                .description("Test description")
                .dueDate(LocalDate.now())
                .id(1L)
                .build();
    }

    @Test
    void TaskService_getTasks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task), pageable, 1);

        when(taskRepository.findByProjectId(1L, pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        List<TaskDto> result = taskService.getTasks(1L, pageable);

        assertEquals(1, result.size());
        assertEquals("Test task", result.getFirst().getTitle());
        verify(taskRepository, times(1)).findByProjectId(1L, pageable);
        verify(taskMapper, times(1)).toDto(task);
    }

    @Test
    void TaskService_updateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto updatedTaskDto =  TaskDto.builder()
                .title("Updated Task")
                .description("Updated description")
                .status(ProjectStatus.TO_DO)
                .dueDate(LocalDate.now().plusDays(1))
                .build();


        TaskDto result = taskService.updateTask(1L, updatedTaskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toDto(task);
    }

    @Test
    void testUpdateTask_NotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, taskDto));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void TaskService_deleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        boolean result = taskService.deleteTask(1L);

        assertTrue(result);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_NotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void TaskService_createTask() {

        List<TaskDto> taskDtos = Arrays.asList(taskDto);
        List<Task> tasks = Arrays.asList(task);
        when(taskMapper.toEntity(taskDto)).thenReturn(task);

        boolean result = taskService.createTask(1L, taskDtos);

        assertTrue(result);
        verify(taskRepository, times(1)).saveAll(tasks);
        verify(taskMapper, times(1)).toEntity(taskDto);
    }

    @Test
    void TaskService_filterTasks() {
        LocalDate dueDate = LocalDate.now();
        List<Task> tasks = Arrays.asList(task);
        String status = String.valueOf(ProjectStatus.TO_DO);

        when(taskRepository.findByStatusAndDueDate(status, dueDate)).thenReturn(tasks);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        List<TaskDto> result = taskService.filterTasks(status, dueDate);

        assertEquals(1, result.size());
        assertEquals(taskDto.getTitle(), result.getFirst().getTitle());
        verify(taskRepository, times(1)).findByStatusAndDueDate(status, dueDate);
        verify(taskMapper, times(1)).toDto(task);
    }
}