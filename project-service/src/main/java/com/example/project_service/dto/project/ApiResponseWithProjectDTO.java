package com.example.project_service.dto.project;


import com.example.project_service.dto.ApiResponse;
import com.example.project_service.entity.Project;

public class ApiResponseWithProjectDTO  extends ApiResponse<Project> {
public ApiResponseWithProjectDTO(String message, Project data){super(message, data);}
}
