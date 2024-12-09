package com.example.project_service.service;

import com.example.project_service.client.FileStorageClient;
import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.task.TaskCreateDTO;
import com.example.project_service.dto.task.TaskDTO;
import com.example.project_service.dto.task.TaskResponseDTO;
import com.example.project_service.dto.task.TaskUpdateDTO;
import com.example.project_service.entity.Task;
import com.example.project_service.entity.Type;
import com.example.project_service.entity.enums.TaskStatus;
import com.example.project_service.repository.ProjectRepository;
import com.example.project_service.repository.TaskRepository;
import com.example.project_service.repository.TypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;
    private final TypeRepository taskGroupRepository;
    private final ProjectRepository projectRepository;
    private final FileStorageClient fileStorageClient;

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
            response.setMessage("Task group not found with id:" + id);
        } else {
            Type taskGroup = byId.get();
            response.setData(taskGroup);
            response.setMessage("Task group found");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteTaskGroup(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<Type> byId = taskGroupRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Task group not found with id:" + id);
        } else {
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
            task.setStatus(TaskStatus.TO_DO);

            if (taskGroupRepository.findById(taskCreateDTO.getGroup_id()).isEmpty()) {
                response.setMessage("Task group not found with : " + taskCreateDTO.getGroup_id());
            } else {
                task.setTaskGroup(taskGroupRepository.findById(taskCreateDTO.getGroup_id()).get());
            }
            if (projectRepository.findById(taskCreateDTO.getProject_id()).isEmpty()) {
                response.setMessage("Project group not found with : " + taskCreateDTO.getGroup_id());
            } else {
                task.setProject(projectRepository.findById(taskCreateDTO.getProject_id()).get());
            }

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


    public ResponseEntity<ApiResponse<TaskResponseDTO>> putURL(Long id, List<String> url) {
        ApiResponse<TaskResponseDTO> response = new ApiResponse<>();
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Project not found with id : " + id);
        } else {
            Task task = byId.get();

            task.setUrlList(url);
            Task save = taskRepository.save(task);
            response.setMessage("URL successfully assigned");
            response.setData(new TaskResponseDTO(save));
        }
        return ResponseEntity.ok(response);
    }

    // Task ga file qo'shish
    public ResponseEntity<ApiResponse<TaskResponseDTO>> putFiles(Long id, List<MultipartFile> files) {
        ApiResponse<TaskResponseDTO> response = new ApiResponse<>();
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Task not found with id : " + id);
        } else {
            Task project = byId.get();
            List<String> fileList = project.getFilePathList();
            for (MultipartFile file : files) {
                fileList.add(fileStorageClient.uploadAttachmentToFIleSystem(file).getBody());
            }
            project.setFilePathList(fileList);
            Task save = taskRepository.save(project);
            response.setMessage("File(s) successfully assigned");
            response.setData(new TaskResponseDTO(save));
        }
        return ResponseEntity.ok(response);

    }


    public ResponseEntity<ApiResponse<TaskDTO>> getById(Long id) {
        ApiResponse<TaskDTO> response = new ApiResponse<>();
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Task not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Found");
        response.setData(new TaskDTO(byId.get()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<?>> getAll() {
        ApiResponse<List<Task>> response = new ApiResponse<>();
        List<Task> partners = taskRepository.findAll();
        response.setMessage("Found " + partners.size() + " task(s)");
        response.setData(new ArrayList<>());
        partners.forEach(i -> response.getData().add(i));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> deleteTask(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Task not found with id:" + id);
        } else {
            taskRepository.deleteById(id);
            response.setMessage("Task successfully deleted");
        }

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<ApiResponse<TaskResponseDTO>> update(TaskUpdateDTO updateDTO) {
        ApiResponse<TaskResponseDTO> response = new ApiResponse<>();
        if (updateDTO.getId() == null) {
            response.setMessage("ID can not be null");
        }
        {
            Optional<Task> byId = taskRepository.findById(updateDTO.getId());
            if (byId.isEmpty()) {
                response.setMessage("Task not found with id : " + updateDTO.getId());
            } else {
                Task task = byId.get();
                if (updateDTO.getName() != null || !updateDTO.getName().isBlank()) {
                    task.setName(updateDTO.getName());
                }
                if (updateDTO.getDeadline() != null) {
                    task.setDeadline(updateDTO.getDeadline());
                }
                if (updateDTO.getDescription() != null || !updateDTO.getDescription().isBlank()) {
                    task.setDescription(updateDTO.getDescription());
                }
                if (updateDTO.getSpentTime() != null || !updateDTO.getSpentTime().isBlank()) {
                    task.setSpentTime(updateDTO.getSpentTime());
                }
                if (updateDTO.getEstimatedTime() != null) {
                    task.setEstimatedTime(updateDTO.getEstimatedTime());
                }
                response.setData(new TaskResponseDTO(task));
                response.setMessage("Task successfully updated");
            }
        }

        return ResponseEntity.ok(response);
    }
}
