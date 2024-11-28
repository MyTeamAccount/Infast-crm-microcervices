package com.example.project_service.service;

import com.example.project_service.client.FileStorageClient;
import com.example.project_service.dto.ApiResponse;
import com.example.project_service.dto.project.ProjectCreateDTO;
import com.example.project_service.dto.project.ProjectResponse;
import com.example.project_service.entity.Project;
import com.example.project_service.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


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


   /* public ResponseEntity<ApiResponse<ProjectResponse>> putURL(Long id,String url){
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
            List<File> fileList=project.getFile();
            for (MultipartFile file : files) {
                fileList.add(fileService.save(file));
            }
            project.setFile(fileList);
            Project save = projectRepository.save(project);
            response.setMessage("File(s) successfully assigned");
            response.setData(new ProjectResponse(save));
        }
        return ResponseEntity.ok(response);

    }*/
}
