package com.example.project_service.dto.task;

import com.example.project_service.dto.UserDto;
import com.example.project_service.dto.project.ProjectResponse;
import com.example.project_service.entity.Task;
import com.example.project_service.entity.Type;
import com.example.project_service.entity.enums.Priority;
import com.example.project_service.entity.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponseDTO {

    Long id;

    ProjectResponse project;

    String name;

    String description;

    TaskStatus status;

    Priority priority;

    Duration estimatedTime;

    Duration spentTime;

    LocalDate deadline;

    LocalDate createdAt;

    Type taskGroup;

    UserDto assignee;

    List<String> urlList;

    List<String> filePathList;


    public TaskResponseDTO(Task entity){
        this.id=entity.getId();
        this.project=new ProjectResponse(entity.getProject());
        this.name=entity.getName();
        this.description=entity.getDescription();
        this.status=entity.getStatus();
        this.priority=entity.getPriority();
        this.estimatedTime=entity.getEstimatedTime();
        this.spentTime=entity.getSpentTime();
        this.deadline=entity.getDeadline();
        this.createdAt=entity.getCreatedAt();
        this.taskGroup=entity.getTaskGroup();
        this.urlList=entity.getUrlList();
        this.filePathList=entity.getFilePathList();
    }

}
