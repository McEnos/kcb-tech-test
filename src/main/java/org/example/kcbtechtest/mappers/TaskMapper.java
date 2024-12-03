package org.example.kcbtechtest.mappers;

import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TaskMapper {
    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);
}
