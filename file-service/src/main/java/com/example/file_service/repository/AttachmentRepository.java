package com.example.file_service.repository;

import com.example.file_service.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    Optional<Attachment> findByName(String name);

    @Query("SELECT a FROM Attachment a WHERE a.name = :nameOrId OR a.id = :nameOrId")
    Optional<Attachment> findByNameOrId(@Param("nameOrId") String nameOrId);
}
