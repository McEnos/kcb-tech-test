package org.example.kcbtechtest.mappers;

import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);

    ProjectDto toDto(Project project);
}
