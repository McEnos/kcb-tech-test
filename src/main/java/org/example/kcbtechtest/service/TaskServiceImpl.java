package org.example.kcbtechtest.service;

import lombok.RequiredArgsConstructor;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.exception.TaskNotFoundException;
import org.example.kcbtechtest.mappers.TaskMapper;
import org.example.kcbtechtest.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDto> getTasks(Long projectId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByProjectId(projectId, pageable);
        return tasks.map(taskMapper::toDto).stream().toList();
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(("Task not found")));
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public boolean deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
        return true;
    }

    @Override
    public boolean createTask(Long projectId, List<TaskDto> taskDto) {
        List<Task> tasks = new ArrayList<>();
        taskDto.stream().map(taskMapper::toEntity).forEach(tasks::add);
        taskRepository.saveAll(tasks);
        return true;
    }

    @Override
    public List<TaskDto> filterTasks(String status, LocalDate dueDate) {
        List<Task> byStatusAndDueDate = taskRepository.findByStatusAndDueDate(status, dueDate);
        return byStatusAndDueDate.stream().map(taskMapper::toDto).toList();
    }
}
