package com.example.project_service.dto.task;

import com.example.project_service.dto.ApiResponse;
import com.example.project_service.entity.Task;

import java.util.List;

public class ApiResponseWithTaskList extends ApiResponse<List<Task>> {
    ApiResponseWithTaskList(String message,List<Task> data){super(message, data);}
}
