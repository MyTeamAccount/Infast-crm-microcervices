package com.example.project_service.dto.task;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateDTO {

    String name;

    Long taskGroup_id;

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    Long assignee_id;

    String description;

    Long project_id;

    String url;
}
