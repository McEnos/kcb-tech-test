package org.example.kcbtechtest.repository;

import org.assertj.core.api.Assertions;
import org.example.kcbtechtest.entity.Task;
import org.example.kcbtechtest.enums.ProjectStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;


    @Test
    void TaskRepository_SaveAll_ReturnsSavedTask() {
        //Arrange
        Task task = Task.builder().status(ProjectStatus.TO_DO)
                .title("Test title")
                .description("Task description")
                .dueDate(LocalDate.now()).build();
        //Act
        Task save = taskRepository.save(task);
        //Assert
        assertNotNull(save);
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    void findByStatusAndDueDate() {
        LocalDate today = LocalDate.now();
        String status = ProjectStatus.TO_DO.toString();

        Task task1 = Task.builder().status(ProjectStatus.TO_DO)
                .title("Test title")
                .description("Task description")
                .dueDate(LocalDate.now()).build();

        Task task2 = Task.builder().status(ProjectStatus.TO_DO)
                .dueDate(LocalDate.now())
                .title("Title two").description("Description two").build();

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByStatusAndDueDate(status, today);

        Assertions.assertThat(tasks).isNotEmpty();
        Assertions.assertThat(tasks.size()).isEqualTo(2);


    }

    @Test
    void findByProjectId() {
    }
}