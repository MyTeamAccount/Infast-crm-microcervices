package com.example.project_service.dto.project;

import com.example.project_service.entity.Project;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {

    Long id;

    String name;

    String projectNumber;

    LocalDate startDate;

    LocalDate deadline;

    String priority;

    String description;

    Long reporter_id;

    List<String> url;

    LocalDate createdDate;

    String photoPath;

    List<String> filePathList;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.projectNumber = project.getProjectNumber();
        this.startDate = project.getStartDate();
        this.deadline = project.getDeadline();
        this.priority = String.valueOf(project.getPriority());
        this.description = project.getDescription();
        this.createdDate = project.getCreatedAt();
        this.photoPath = project.getPhotoPath();

        if (project.getUrl() != null) {
            this.url = project.getUrl();
        }
        if (project.getFilePathList() != null) {
            this.filePathList = project.getFilePathList();
        }

    }
}
