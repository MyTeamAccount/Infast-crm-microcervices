package com.example.project_service.controller;

import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.project.ApiWithProjectDTO;
import com.example.project_service.dto.project.ProjectCreateDTO;
import com.example.project_service.dto.project.ProjectResponse;
import com.example.project_service.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiWithProjectDTO.class))
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
}
