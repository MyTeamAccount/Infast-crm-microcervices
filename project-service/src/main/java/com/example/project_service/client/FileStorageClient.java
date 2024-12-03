package com.example.project_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", path = "/v1/file-storage")
public interface FileStorageClient {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadAttachmentToFIleSystem(@RequestPart("attachment") MultipartFile file);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteAttachmentFromFileSystem(@PathVariable String id);
}