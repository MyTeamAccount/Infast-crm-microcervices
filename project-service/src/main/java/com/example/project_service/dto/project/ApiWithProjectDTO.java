package com.example.project_service.dto.project;


import com.example.project_service.dto.ApiResponse;

public class ApiWithProjectDTO  extends ApiResponse<ProjectCreateDTO> {
public ApiWithProjectDTO(String message, ProjectCreateDTO data){super(message, data);}
}
