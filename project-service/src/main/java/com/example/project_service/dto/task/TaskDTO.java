package com.example.project_service.dto.task;

import com.example.project_service.entity.Project;
import com.example.project_service.entity.Task;
import com.example.project_service.entity.Type;
import com.example.project_service.entity.enums.Priority;
import com.example.project_service.entity.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDTO {
    private Long id;

    private Project project;

    private String name;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private String estimatedTime;

    private String spentTime;

    private LocalDate deadline;

    private LocalDate createdAt;

    private Type taskGroup;

    private Long assignee_id;

    private List<String> urlList;

    private List<String> filePathList;

    public  TaskDTO(Task task){
        this.id=task.getId();
        this.project=task.getProject();
        this.name=task.getName();
        this.description=task.getDescription();
        this.status=task.getStatus();
        this.priority=task.getPriority();
        this.estimatedTime=task.getEstimatedTime();
        this.spentTime=task.getSpentTime();
        this.deadline=task.getDeadline();
        this.createdAt=task.getCreatedAt();
        this.taskGroup=task.getTaskGroup();
        this.assignee_id=task.getAssignee_id();
        this.urlList=task.getUrlList();
        this.filePathList=task.getFilePathList();
    }
}
