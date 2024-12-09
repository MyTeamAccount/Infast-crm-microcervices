package com.example.project_service.controller;

import com.example.project_service.constants.SwaggerConstants;
import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.project.*;
import com.example.project_service.entity.Project;
import com.example.project_service.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @Operation(summary = "Add new 'Project'")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Successfully created, status '201' ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithProjectDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If error occurs, check response message",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<ProjectResponse>> create(
            @Parameter(
                    name = "json",
                    description = "Insert text data as JSON format",
                    required = true,
                    schema = @Schema(implementation = ProjectCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,
            @Parameter(
                    name = "photo",
                    description = "Photo file to be uploaded for the project. Supported formats: .jpg, .png, .svg",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))
            )
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return projectService.addProject(json, photo);
    }


    // Projectga URL o'rnatish
    @PutMapping(value = "/assign-url/{id}")
    @Operation(summary = "Assign or update URLs to project")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Successfully assigned or updated, status '201'",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithProjectDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "If error occurs, check response message",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<ProjectResponse>> putURL(
            @Parameter(
                    name = "url",
                    description = """
                                Url to be assigned for the project.
                            """,
                    required = true,
                    content = @Content(mediaType = "string",
                            schema = @Schema(type = "array")
                    )
            )
            @RequestParam List<String> url,
            @PathVariable Long id
    ) {
        return projectService.putURL(id, url);
    }


    @Operation(summary = "This API used for assigned files to project ")
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
    public ResponseEntity<ApiResponse<ProjectResponse>> putFiles(
            @Parameter(
                    name = "file",
                    description = """
                                File to be uploaded for the project. Select file image, video or document file format .
                            """,
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "array")
                    )
            )
            @RequestPart(value = "files") List<MultipartFile> files,
            @PathVariable Long id
    ) {
        return projectService.putFiles(id, files);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a project by its ID ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved project .",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWithProjectDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Partner not found - Партнер не найден", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))})
    })
    public ResponseEntity<ApiResponse<Project>> findById(
            @Parameter(
                    name = "id",
                    description = "ID of the project to be retrieved ",
                    required = true)
            @PathVariable Long id
    ) {
        return projectService.getById(id);
    }


    @GetMapping("/get-all")
    @Operation(summary = "This API used to retrieve all projects ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all projects",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiLisProjectDTO.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request ", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<?>> findAll() {
        return projectService.getAll();
    }




    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a project  by its ID .")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted the project .",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
                    }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "project not found if the partner ID doesn't exist .", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))}),
    })
    public ResponseEntity<ApiResponse<?>> deleteTask(
            @Parameter(
                    name = "id",
                    description = "ID of the project to be deleted ",
                    required = true)
            @PathVariable Long id
    )
    {
        return projectService.delete(id);
    }



    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "You get 200 status code when data succesfully edited ",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseWithProjectDTO.class)
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
    public ResponseEntity<ApiResponse<ProjectResponse>> update(
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
                                            value = SwaggerConstants.PROJECT_FULL_FORM
                                    ),
                                    @ExampleObject(
                                            name = "Edit custom field ",
                                            description = "Send field which you want ",
                                            value = SwaggerConstants.CUSTOM_FIELD
                                    )
                            }
                    )
            )
            @RequestBody ProjectUpdateDTO newDTO) {

        return projectService.update(newDTO);

    }

}
