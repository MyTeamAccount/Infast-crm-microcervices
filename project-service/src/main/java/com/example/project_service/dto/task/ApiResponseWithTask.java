package com.example.project_service.dto.task;

import com.example.project_service.dto.ApiResponse;
import com.example.project_service.entity.Task;

public class ApiResponseWithTask extends ApiResponse<Task> {
    ApiResponseWithTask(String message, Task data){super(message, data);}
}
