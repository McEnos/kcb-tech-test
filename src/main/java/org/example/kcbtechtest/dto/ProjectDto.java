package org.example.kcbtechtest.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "ProjectDto", description = "A project Data transfer Object"
)
public class ProjectDto {
    @Schema(
            name = "id", example = "1", description = "primary key for project, should not be passed when creating a new record"
    )
    private Long id;
    @Schema(
            name = "name", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Assignment",
            description = "Specific name for a project"
    )
    private String name;
    @Schema(
            name = "description", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Assignment one",
            description = "Description given to a project"
    )
    private String description;
}
