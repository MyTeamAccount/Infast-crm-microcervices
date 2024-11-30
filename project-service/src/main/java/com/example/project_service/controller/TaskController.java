package com.example.project_service.controller;

import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.ApiResponseWithTaskGroupDTO;
import com.example.project_service.entity.Type;
import com.example.project_service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping(value = "/add-task-group", consumes = {"multipart/form-data"})
    @Operation(summary = "Add  new 'Task Group' ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "If successfully created you get  status '201' ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTaskGroupDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If get 400 status Please read response 'message' - Если получить статус 400, прочитайте ответное сообщение 'message'",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<Type>> addTaskGroup(
            @Parameter(
                    name = "json",
                    description = "Insert Task Group name - Вставьте название компании",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @RequestPart(value = "name") String name
    ){
        return taskService.createTaskGroup(name);
    }


    @GetMapping("/get-all-task-group")
    @Operation(summary = "This API used to retrieve all task groups ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all task groups ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTaskGroupDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request .", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<List<Type>>> findAll()
    {
        return taskService.getAllTaskGroup();
    }


    @GetMapping("/get-task-group-by-id/{id}")
    @Operation(summary = "This API used to retrieve a task group by its ID .")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved partner - Партнер успешно найден",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTaskGroupDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Task group not found ", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))})
    })
    public ResponseEntity<ApiResponse<Type>> findById(
            @Parameter(
                    name = "id",
                    description = "ID of the task group to be retrieved",
                    required = true)
            @PathVariable Long id
    )
    {
        return taskService.getTaskGroupById(id);
    }


    @DeleteMapping("/delete-task-group/{id}")
    @Operation(summary = "This API used to delete a task group by its ID .")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted the task group .",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "task group not found if the partner ID doesn't exist .", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<?>> delete(
            @Parameter(
                    name = "id",
                    description = "ID of the task group to be deleted ",
                    required = true)
            @PathVariable Long id
    )
    {
        return taskService.deleteTaskGroup(id);
    }
}
