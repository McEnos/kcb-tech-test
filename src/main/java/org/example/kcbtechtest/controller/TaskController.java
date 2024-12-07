package org.example.kcbtechtest.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.kcbtechtest.dto.TaskDto;
import org.example.kcbtechtest.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<TaskDto> updateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskId, taskDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> deleteTask(@PathVariable("taskId") Long taskId) {
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Fetch Tasks",
            description = "An endpoint used to fetch a list of paginated tasks resource"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks fetched successfully."),
            @ApiResponse(responseCode = "404", description = "Provided endpoint is wrong"),
            @ApiResponse(responseCode = "400", description = "Request sent is in wrong format"),
            @ApiResponse(responseCode = "500", description = "Server experienced challenge processing your request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam  String status,
                                                  @RequestParam  LocalDate date) {
        return new ResponseEntity<>(taskService.filterTasks(status, date), HttpStatus.OK);

    }


}
