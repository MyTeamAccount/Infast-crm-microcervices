package com.example.project_service.repository;

import com.example.project_service.entity.TimeTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingRepository extends JpaRepository<TimeTracking,Long> {
}
