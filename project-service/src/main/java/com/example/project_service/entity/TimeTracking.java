package com.example.project_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_tracking")
@Data
public class TimeTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task; // Ushbu vaqt kuzatuvi qaysi vazifa bilan bog'liq

    private Long employee; // Ushbu vaqt kuzatuvini kim amalga oshirdi

    @Column(nullable = false)
    private Duration timeSpent; // Vazifa uchun sarflangan vaqt

    @Column(nullable = false)
    private LocalDateTime loggedAt; // Vaqt kuzatish qachon amalga oshirildi

    @Column(length = 500)
    private String workDescription; // Ish haqida qisqacha ma'lumot (ixtiyoriy)
}

