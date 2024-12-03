package org.example.kcbtechtest.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(
        name = "TaskDto", description = "A Task Data transfer Object"
)
public class TaskDto {
    @Schema(
            name = "id", example = "1", description = "primary key for task, should not be passed when creating a new record"
    )

    private int id;

    @Schema(
            name = "title", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Task",
            description = "Specific name for a project"
    )

    private String title;
    @Schema(
            name = "description", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Task to be done by tomorrow",
            description = "Description given to a task"
    )
    private String description;
    @Schema(
            name = "dueDate", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "03/12/2024",
            description = "The date when the assignment is due"
    )
    private LocalDate dueDate;
    @Schema(
            name = "projectId", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1",
            description = "Project Id tied to this task"
    )
    private Long projectId;
}
