package com.example.file_service.controller;


import com.example.file_service.entity.Attachment;
import com.example.file_service.payload.AttachmentDto;
import com.example.file_service.payload.ResponseMessage;
import com.example.file_service.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "File-storage")
@RequestMapping("/v1/file-storage")
public class AttachmentController
{
    private final AttachmentService attachmentService;
    @Operation(summary = "Upload dock,image,or pdf, or mp4")
    @PostMapping(value = "/upload",consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<?> upload(
            @Parameter(
                    description = "Select dock,image,or pdf, or mp4",
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(
                                    type = "string",
                                    format = "binary",
                                    example = "dock,image,or pdf, or mp4"
                            )))
            @RequestPart(name = "file", required = false) List<MultipartFile> photo)
    {
        return ResponseEntity.status(201).body(attachmentService.upload(photo));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Show a photo or video by name - Показать фото")
    public ResponseEntity<byte[]> getAttachmentByName(@PathVariable(name = "name") String name)
    {
        return attachmentService.findByName(name);
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadAttachmentToFIleSystem(@RequestPart("attachment") MultipartFile file){
        Attachment save = attachmentService.save(file);
        return ResponseEntity.status(201).body(save.getUrl());
    }



    @GetMapping("/id/{id}")
    @Operation(summary = "Show a dock,image,or pdf, or mp4 by id- Показать фото")
    public ResponseEntity<byte[]> getAttachmentById(@PathVariable(name = "id") String  id)
    {
        return attachmentService.findById(id);
    }

    @GetMapping("/{nameOrId}")
    @Operation(summary = "Show a dock,image,or pdf, or mp4 by name or ID")
    public ResponseEntity<byte[]> getAttachmentByNameOrId(@PathVariable(name = "nameOrId") String nameOrId) {
        return attachmentService.findByNameOrId(nameOrId);
    }



    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Attachment updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AttachmentDto.class)))})
    @Operation(summary = "Replace attachment to another")
    @Parameters({
            @Parameter(name = "id", description = "Show 'id' in url path for which attachment is updated ", required = true),
    })
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateAttachment(
            @PathVariable(name = "id")
            String id,

            @Parameter(
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(
                                    type = "string",
                                    format = "binary",
                                    example = "dock,image,or pdf, or mp4"
                            )))
            @RequestPart(name = "new-attachment", required = false) MultipartFile newAttachment)
    {
        return ResponseEntity.status(200).body(attachmentService.update(id, newAttachment));
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete dock,image,or pdf, or mp4 from table and also file system")
    public ResponseEntity<?> deleteAttachment(
            @PathVariable
            @Parameter(description = "select 'id' which attachment must deleted ")
            String  id)
    {
        return ResponseEntity.status(200).body(attachmentService.delete(id));
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteAttachmentFromFileSystem(@PathVariable String id){
        ResponseMessage delete = attachmentService.delete(id);
        return ResponseEntity.status(delete.getStatus()?200:400).build();
    }


}

