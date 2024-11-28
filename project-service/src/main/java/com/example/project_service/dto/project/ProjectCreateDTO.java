package com.example.project_service.dto.project;

import com.example.project_service.entity.enums.Priority;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectCreateDTO {

    String name;

    LocalDate startDate;

    LocalDate deadline;

    Priority priority;

    String description;

    String url;
}
