package com.example.project_service.dto.project;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDTO {

    Long id;

     String name;

     String projectNumber;

     LocalDateTime startDateTime;

     LocalDateTime endDateTime;

     String priority;

     String description;

     String reporter_id;

     String url;

     String photoPath;

     List<String> filePathList;

}
