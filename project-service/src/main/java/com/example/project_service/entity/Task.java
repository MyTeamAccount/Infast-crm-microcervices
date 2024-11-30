package com.example.project_service.entity;

import com.example.project_service.dto.task.TaskCreateDTO;
import com.example.project_service.entity.enums.Priority;
import com.example.project_service.entity.enums.TaskStatus;
import com.example.project_service.exeption.ApiException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private Duration estimatedTime;

    private Duration spentTime;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private LocalDate createdAt;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Type taskGroup;

    private Long assignee_id;

    @ElementCollection
    private List<String> urlList;

    @ElementCollection
    private List<String> filePathList;

    public Task(TaskCreateDTO taskCreateDTO){
        if (taskCreateDTO==null){
            return;
        }
        this.name=taskCreateDTO.getName();
        this.description=taskCreateDTO.getDescription();
        this.estimatedTime=taskCreateDTO.getEstimate();
        this.deadline=taskCreateDTO.getDeadline();
        this.priority=taskCreateDTO.getPriority();
        this.assignee_id=taskCreateDTO.getAssigneeId();

    }

}
