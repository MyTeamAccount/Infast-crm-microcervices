package com.example.project_service.controller;

import com.example.project_service.constants.SwaggerConstants;
import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.ApiResponseWithTaskGroupDTO;
import com.example.project_service.dto.project.ApiResponseWithProjectDTO;
import com.example.project_service.dto.project.ProjectUpdateDTO;
import com.example.project_service.dto.task.*;
import com.example.project_service.entity.Type;
import com.example.project_service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @Operation(summary = "Add new 'Task'")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Successfully created, status '201' ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTask.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If error occurs, check response message",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<TaskResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "Insert text data as JSON format",
                    required = true,
                    schema = @Schema(implementation = TaskCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json
    ) {
        return taskService.addTask(json);
    }


    // Task ga URL o'rnatish
    @PutMapping(value = "/assign-url/{id}")
    @Operation(summary = "Assign or update URLs to task")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Successfully assigned or updated, status '201'",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTask.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If error occurs, check response message",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<TaskResponseDTO>> putURL(
            @Parameter(
                    name = "url",
                    description = """
                                Url to be assigned for the task.
                            """,
                    required = true,
                    content = @Content(mediaType = "string",
                            schema = @Schema(type = "array")
                    )
            )
            @RequestParam List<String> url,
            @PathVariable Long id
    ) {
        return taskService.putURL(id, url);
    }



    @Operation(summary = "This API used for assigned files to task ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully assigned you get  status '201' '",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithProjectDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If get 400 status Please read response 'message' ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping(consumes = {"multipart/form-data"}, value = "/assign-file/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> putFiles(
            @Parameter(
                    name = "file",
                    description = """
                                File to be uploaded for the task. Select file image, video or document file format .
                            """,
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "array")
                    )
            )
            @RequestPart(value = "files") List<MultipartFile> files,
            @PathVariable Long id
    ) {
        return taskService.putFiles(id, files);
    }




    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a task by its ID ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved project .",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTask.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Partner not found - Партнер не найден", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))})
    })
    public ResponseEntity<ApiResponse<TaskDTO>> findTaskById(
            @Parameter(
                    name = "id",
                    description = "ID of the task to be retrieved ",
                    required = true)
            @PathVariable Long id
    ) {
        return taskService.getById(id);
    }


    @GetMapping("/get-all")
    @Operation(summary = "This API used to retrieve all tasks ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all tasks",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithTaskList.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request ", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<?>> findAllTasks() {
        return taskService.getAll();
    }



    @DeleteMapping("/delete-task/{id}")
    @Operation(summary = "This API used to delete a task  by its ID .")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted the task .",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "task not found if the partner ID doesn't exist .", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<?>> deleteTask(
            @Parameter(
                    name = "id",
                    description = "ID of the task to be deleted ",
                    required = true)
            @PathVariable Long id
    )
    {
        return taskService.deleteTask(id);
    }



    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "You get 200 status code when data succesfully edited ",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseWithTask.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 Please READ RESPONSE MESSAGE!!! ",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    ))
    })
    @Operation(summary = "Update and edit project")
    @PutMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = SwaggerConstants.UPDATE_DESCRIPTION,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectUpdateDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Edit All fields ",
                                            description = "Edit All fields at the same time ",
                                            value = SwaggerConstants.TASK_FULL_FORM
                                    ),
                                    @ExampleObject(
                                            name = "Edit custom field ",
                                            description = "Send field which you want ",
                                            value = SwaggerConstants.CUSTOM_FIELD
                                    )
                            }
                    )
            )
            @RequestBody TaskUpdateDTO newDTO) {

        return taskService.update(newDTO);

    }

}
