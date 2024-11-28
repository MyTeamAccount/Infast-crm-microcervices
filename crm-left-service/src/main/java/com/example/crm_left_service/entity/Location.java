package com.example.crm_left_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;//lakatsiya nomi

    @Column(precision = 10, scale = 6, nullable = false) // Maksimal aniqlik: 6 kasr raqami
    private BigDecimal latitude; // Kenglik (52.520008 kabi)

    @Column(precision = 10, scale = 6, nullable = false) // Maksimal aniqlik: 6 kasr raqami
    private BigDecimal longitude; // Uzunlik (13.404954 kabi)
}
