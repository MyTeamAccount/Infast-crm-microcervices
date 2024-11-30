package com.example.project_service.service;

import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.task.TaskCreateDTO;
import com.example.project_service.dto.task.TaskResponseDTO;
import com.example.project_service.entity.Task;
import com.example.project_service.entity.Type;
import com.example.project_service.repository.TaskRepository;
import com.example.project_service.repository.TypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;
    private final TypeRepository taskGroupRepository;

    public ResponseEntity<ApiResponse<Type>> createTaskGroup(String name) {
        ApiResponse<Type> response = new ApiResponse<>();
        Type taskGroup = new Type();
        taskGroup.setName(name);
        Type save = taskGroupRepository.save(taskGroup);
        response.setData(save);
        response.setMessage("Task group successfully added");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<Type>>> getAllTaskGroup() {
        ApiResponse<List<Type>> response = new ApiResponse<>();
        List<Type> all = taskGroupRepository.findAll();
        response.setData(all);
        response.setMessage("Found " + all.size() + " task group(s)");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Type>> getTaskGroupById(Long id) {
        ApiResponse<Type> response = new ApiResponse<>();
        Optional<Type> byId = taskGroupRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Task group not found with id:" +id);
        }else {
            Type taskGroup = byId.get();
            response.setData(taskGroup);
            response.setMessage("Task group found");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteTaskGroup(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        Optional<Type> byId = taskGroupRepository.findById(id);
        if (byId.isEmpty()){
            response.setMessage("Task group not found with id:" +id);
        }else {
            taskGroupRepository.deleteById(id);
            response.setMessage("Task group successfully deleted");
        }

        return ResponseEntity.ok(response);

    }



    public ResponseEntity<ApiResponse<TaskResponseDTO>> addTask(String json) {

        ApiResponse<TaskResponseDTO> response = new ApiResponse<>();

        try {
            TaskCreateDTO taskCreateDTO = objectMapper.readValue(json, TaskCreateDTO.class);
            Task task = new Task(taskCreateDTO);
            task.setCreatedAt(LocalDate.now());
            Task save = taskRepository.save(task);
            Task save1 = taskRepository.save(save);
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(save1);
            response.setMessage("Task successfully added ");
            response.setData(taskResponseDTO);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(response);

    }
}
