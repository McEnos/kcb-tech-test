package org.example.kcbtechtest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Project;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.example.kcbtechtest.exception.TaskNotFoundException;
import org.example.kcbtechtest.mappers.TaskMapper;
import org.example.kcbtechtest.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskServiceImplDiffblueTest {
    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    /**
     * Test {@link TaskServiceImpl#updateTask(Long, TaskDto)}.
     * <p>
     * Method under test: {@link TaskServiceImpl#updateTask(Long, TaskDto)}
     */
    @Test
    @DisplayName("Test updateTask(Long, TaskDto)")
    void testUpdateTask() {
        // Arrange
        when(taskRepository.findById(Mockito.<Long>any())).thenThrow(new TaskNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.updateTask(1L, null));
        verify(taskRepository).findById(eq(1L));
    }

    /**
     * Test {@link TaskServiceImpl#updateTask(Long, TaskDto)}.
     * <p>
     * Method under test: {@link TaskServiceImpl#updateTask(Long, TaskDto)}
     */
    @Test
    @DisplayName("Test updateTask(Long, TaskDto)")
    void testUpdateTask2() {
        // Arrange
        Project project = new Project();
        project.setDescription("The characteristics of someone or something");
        project.setId(1L);
        project.setName("Name");
        project.setTasks(new HashSet<>());

        Task task = new Task();
        task.setDescription("The characteristics of someone or something");
        task.setDueDate(LocalDate.of(1970, 1, 1));
        task.setId(1L);
        task.setProject(project);
        task.setStatus(ProjectStatus.TO_DO);
        task.setTitle("Dr");
        Optional<Task> ofResult = Optional.of(task);

        Project project2 = new Project();
        project2.setDescription("The characteristics of someone or something");
        project2.setId(1L);
        project2.setName("Name");
        project2.setTasks(new HashSet<>());

        Task task2 = new Task();
        task2.setDescription("The characteristics of someone or something");
        task2.setDueDate(LocalDate.of(1970, 1, 1));
        task2.setId(1L);
        task2.setProject(project2);
        task2.setStatus(ProjectStatus.TO_DO);
        task2.setTitle("Dr");
        when(taskRepository.save(Mockito.<Task>any())).thenReturn(task2);
        when(taskRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TaskDto.TaskDtoBuilder descriptionResult = TaskDto.builder()
                .description("The characteristics of someone or something");
        TaskDto buildResult = descriptionResult.dueDate(LocalDate.of(1970, 1, 1))
                .id(1L)
                .projectId(1L)
                .status(ProjectStatus.TO_DO)
                .title("Dr")
                .build();
        when(taskMapper.toDto(Mockito.<Task>any())).thenReturn(buildResult);
        TaskDto.TaskDtoBuilder descriptionResult2 = TaskDto.builder()
                .description("The characteristics of someone or something");
        TaskDto taskDto = descriptionResult2.dueDate(LocalDate.of(1970, 1, 1))
                .id(1L)
                .projectId(1L)
                .status(ProjectStatus.TO_DO)
                .title("Dr")
                .build();

        // Act
        TaskDto actualUpdateTaskResult = taskServiceImpl.updateTask(1L, taskDto);

        // Assert
        verify(taskMapper).toDto(isA(Task.class));
        verify(taskRepository).findById(eq(1L));
        verify(taskRepository).save(isA(Task.class));
        assertEquals(taskDto, actualUpdateTaskResult);
    }

    /**
     * Test {@link TaskServiceImpl#updateTask(Long, TaskDto)}.
     * <p>
     * Method under test: {@link TaskServiceImpl#updateTask(Long, TaskDto)}
     */
    @Test
    @DisplayName("Test updateTask(Long, TaskDto)")
    void testUpdateTask3() {
        // Arrange
        Project project = new Project();
        project.setDescription("The characteristics of someone or something");
        project.setId(1L);
        project.setName("Name");
        project.setTasks(new HashSet<>());

        Task task = new Task();
        task.setDescription("The characteristics of someone or something");
        task.setDueDate(LocalDate.of(1970, 1, 1));
        task.setId(1L);
        task.setProject(project);
        task.setStatus(ProjectStatus.TO_DO);
        task.setTitle("Dr");
        Optional<Task> ofResult = Optional.of(task);

        Project project2 = new Project();
        project2.setDescription("The characteristics of someone or something");
        project2.setId(1L);
        project2.setName("Name");
        project2.setTasks(new HashSet<>());

        Task task2 = new Task();
        task2.setDescription("The characteristics of someone or something");
        task2.setDueDate(LocalDate.of(1970, 1, 1));
        task2.setId(1L);
        task2.setProject(project2);
        task2.setStatus(ProjectStatus.TO_DO);
        task2.setTitle("Dr");
        when(taskRepository.save(Mockito.<Task>any())).thenReturn(task2);
        when(taskRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(taskMapper.toDto(Mockito.<Task>any())).thenThrow(new TaskNotFoundException("An error occurred"));
        TaskDto.TaskDtoBuilder descriptionResult = TaskDto.builder()
                .description("The characteristics of someone or something");
        TaskDto taskDto = descriptionResult.dueDate(LocalDate.of(1970, 1, 1))
                .id(1L)
                .projectId(1L)
                .status(ProjectStatus.TO_DO)
                .title("Dr")
                .build();

        // Act and Assert
        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.updateTask(1L, taskDto));
        verify(taskMapper).toDto(isA(Task.class));
        verify(taskRepository).findById(eq(1L));
        verify(taskRepository).save(isA(Task.class));
    }

    /**
     * Test {@link TaskServiceImpl#updateTask(Long, TaskDto)}.
     * <ul>
     *   <li>Given {@link TaskRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link TaskServiceImpl#updateTask(Long, TaskDto)}
     */
    @Test
    @DisplayName("Test updateTask(Long, TaskDto); given TaskRepository findById(Object) return empty")
    void testUpdateTask_givenTaskRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Task> emptyResult = Optional.empty();
        when(taskRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.updateTask(1L, null));
        verify(taskRepository).findById(eq(1L));
    }
}
