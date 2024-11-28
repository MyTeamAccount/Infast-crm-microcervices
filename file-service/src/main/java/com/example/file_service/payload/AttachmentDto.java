package com.example.file_service.payload;

import com.example.file_service.entity.Attachment;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDto
{
    String id;

    String url;


    public AttachmentDto(Attachment entity)
    {
        if (entity == null)
            return;
        this.id = entity.getId();
        this.url = entity.getUrl();
    }
}
