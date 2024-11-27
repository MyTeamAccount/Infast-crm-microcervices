package com.example.project_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task; // Ushbu harakat qaysi vazifa bilan bog'liq

    private Long performedBy_id; // Kim tomonidan amalga oshirilgan

    @Column(nullable = false)
    private String action; // Harakat turi (masalan, "Status changed")

    @Column(nullable = false)
    private String description; // Qo'shimcha ma'lumot (masalan, "Priority changed from Medium to High")

    @Column(nullable = false)
    private LocalDateTime performedAt; // Harakat amalga oshirilgan vaqt
}

