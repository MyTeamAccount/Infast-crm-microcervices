package com.example.project_service.dto.task;

import com.example.project_service.entity.Project;
import com.example.project_service.entity.Type;
import com.example.project_service.entity.enums.Priority;
import com.example.project_service.entity.enums.TaskStatus;
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
public class TaskUpdateDTO {

    private Long id;

    private String name;

    private String description;

    private String estimatedTime;

    private String spentTime;

    private LocalDate deadline;
}
