package org.example.kcbtechtest.repository;

import org.example.kcbtechtest.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatusAndDueDate(String status, LocalDate dueDate);

    @Query("select  t from Task t where t.project.id = ?1")
    Page<Task> findByProjectId(Long projectId, Pageable pageable);
}
