package com.example.project_service.dto.project;


import com.example.project_service.dto.ApiResponse;

import java.util.List;

public class ApiLisProjectDTO extends ApiResponse<List<ProjectDTO>>
{
    public ApiLisProjectDTO(String message, List<ProjectDTO> data)
    {
        super(message, data);
    }
}
