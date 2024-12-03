package org.example.kcbtechtest.service;


import org.example.kcbtechtest.dto.TaskDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks(Long pojectId, Pageable pageable);

    TaskDto updateTask(Long taskId, TaskDto taskDto);

    boolean deleteTask(Long taskId);

    boolean createTask(Long projectId, List<TaskDto> taskDto);

    List<TaskDto> filterTasks(String status, LocalDate dueDate);

}
