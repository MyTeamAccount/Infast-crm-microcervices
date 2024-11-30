package com.example.project_service.dto;

import com.example.project_service.entity.Type;

public class ApiResponseWithTaskGroupDTO extends ApiResponse<Type> {
    public ApiResponseWithTaskGroupDTO(String message, Type data){super(message, data);}
}
