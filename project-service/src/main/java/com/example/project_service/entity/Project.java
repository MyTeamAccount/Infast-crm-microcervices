package com.example.project_service.entity;

import com.example.project_service.dto.project.ProjectCreateDTO;
import com.example.project_service.entity.enums.Priority;
import com.example.project_service.exeption.ApiException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String projectNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate updatedAt;

    private Long reporter_id;

    private  String photoPath;

    @ElementCollection
    private List<String> url;

    @ElementCollection
    private List<String> filePathList;


    public Project(ProjectCreateDTO projectCreateDTO) {
        if (projectCreateDTO == null) {
            return;
        }
        this.name=projectCreateDTO.getName();
        this.startDate=projectCreateDTO.getStartDate();
        this.deadline=projectCreateDTO.getDeadline();
        this.priority= projectCreateDTO.getPriority();
        this.description=projectCreateDTO.getDescription();


    }

    @PrePersist
    @PreUpdate
    protected void checkDate() {
        if (startDate != null && deadline != null && startDate.isAfter(deadline)) {
            throw new ApiException(String.format("%s is not after %s", deadline, startDate));
        }
    }

}

