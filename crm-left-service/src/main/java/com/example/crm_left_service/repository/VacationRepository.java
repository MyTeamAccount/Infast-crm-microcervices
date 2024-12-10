package com.example.crm_left_service.repository;


import com.example.crm_left_service.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
