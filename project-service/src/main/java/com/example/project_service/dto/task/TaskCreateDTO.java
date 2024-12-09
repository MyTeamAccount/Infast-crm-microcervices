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
    private Long group_id;
    private String estimate;
    private LocalDate deadline;
    private Priority priority;
    private Long assigneeId;
    private String description;
    private Long project_id;
}
