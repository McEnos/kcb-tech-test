package org.example.kcbtechtest.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.kcbtechtest.dto.ProjectDto;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.service.ProjectService;
import org.example.kcbtechtest.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Project controller",
        description = "Endpoints used to manage project resource"
)
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create Project",
            description = "An endpoint used to create a single project resource"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project created successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.createProject(projectDto), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Fetch Projects",
            description = "An endpoint used to fetch a list of paginated project resource"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects fetched successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(projectService.getProjects(PageRequest.of(page, size)), HttpStatus.OK);

    }

    @Operation(
            summary = "Fetch Project",
            description = "Fetch a single project using project id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project created successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.getProject(projectId), HttpStatus.OK);
    }

    @Operation(
            summary = "Create A list of tasks",
            description = "Create A list of tasks tied to a specific project using project id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tasks created successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @PostMapping("/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> addTask(@PathVariable Long projectId, @RequestBody List<TaskDto> taskDtos) {
        return new ResponseEntity<>(taskService.createTask(projectId, taskDtos), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch Tasks",
            description = "Fetch A list of tasks tied to a specific project using project id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks fetched  successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @GetMapping("/{projectId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Long projectId, @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(taskService.getTasks(projectId, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Project",
            description = "Delete a  project by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tasks deleted  successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> deleteProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.deleteProject(projectId), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ProjectDto> updateTask(@PathVariable("projectId") Long projectId, @RequestBody ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.updateProject(projectId, projectDto), HttpStatus.ACCEPTED);
    }


}
