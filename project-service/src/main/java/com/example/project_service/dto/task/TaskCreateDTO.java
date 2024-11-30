package com.example.project_service.dto.task;

import com.example.project_service.entity.Type;
import com.example.project_service.entity.enums.Priority;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateDTO {

    private String name;
    private Type group_id;
    private Duration estimate;
    private LocalDate deadline;
    private Priority priority;
    private Long assigneeId;
    private String description;
}
