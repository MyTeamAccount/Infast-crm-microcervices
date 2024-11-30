package com.example.project_service.service;

import com.example.project_service.client.FileStorageClient;
import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.project.ApiLisProjectDTO;
import com.example.project_service.dto.project.ProjectCreateDTO;
import com.example.project_service.dto.project.ProjectResponse;
import com.example.project_service.entity.Project;
import com.example.project_service.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ObjectMapper objectMapper;
    private final FileStorageClient fileStorageClient;

    public ResponseEntity<ApiResponse<ProjectResponse>> addProject(String json, MultipartFile photo) {

        ApiResponse<ProjectResponse> response = new ApiResponse<>();

        try {
            ProjectCreateDTO projectCreateDTO = objectMapper.readValue(json, ProjectCreateDTO.class);
            Project project = new Project(projectCreateDTO);
            project.setPhotoPath(String.valueOf(fileStorageClient.uploadImageToFIleSystem(photo)));
            project.setCreatedAt(LocalDate.now());
            Project save = projectRepository.save(project);
            save.setProjectNumber(String.valueOf(projectNumber()));
            if (projectCreateDTO.getUrl() != null) {
                save.setUrl(project.getUrl());
            }
            Project save1 = projectRepository.save(save);
            ProjectResponse projectResponse = new ProjectResponse(save1);
            response.setMessage("Project successfully added ");
            response.setData(projectResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(response);

    }

    private StringBuilder projectNumber() {
        int id = projectRepository.findAll().size();
        int id1 = projectRepository.findAll().size();
        int n = 0;
        StringBuilder sb = new StringBuilder("PN");
        while (id > 0) {
            n++;
            id = id / 10;
        }

        sb.append("0".repeat(Math.max(0, 7 - n)));
        sb.append(id1 + 1);
        return sb;
    }


    public ResponseEntity<ApiResponse<ProjectResponse>> putURL(Long id, List<String> url){
        ApiResponse<ProjectResponse> response=new ApiResponse<>();
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isEmpty()){
            response.setMessage("Project not found with id : "+ id);
        }else {
            Project project = byId.get();

            project.setUrl(url);
            Project save = projectRepository.save(project);
            response.setMessage("URL successfully assigned");
            response.setData(new ProjectResponse(save));
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<ProjectResponse>> putFiles(Long id, List<MultipartFile> files){
        ApiResponse<ProjectResponse> response=new ApiResponse<>();
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isEmpty()){
            response.setMessage("Project not found with id : "+ id);
        }else {
            Project project = byId.get();
            List<String> fileList=project.getFilePathList();
            for (MultipartFile file : files) {
                fileList.add(String.valueOf(fileStorageClient.uploadImageToFIleSystem(file)));
            }
            project.setFilePathList(fileList);
            Project save = projectRepository.save(project);
            response.setMessage("File(s) successfully assigned");
            response.setData(new ProjectResponse(save));
        }
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<ApiResponse<Project>> getById(Long id) {
        ApiResponse<Project> response = new ApiResponse<>();
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isEmpty()) {
            response.setMessage("Project not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Found");
        response.setData(byId.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<?>> getAll() {
        ApiResponse<List<Project>> response = new ApiResponse<>();
        List<Project> partners = projectRepository.findAll();
        response.setMessage("Found " + partners.size() + " project(s)");
        response.setData(new ArrayList<>());
        partners.forEach(i -> response.getData().add(i));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
