package com.example.crm_left_service.entity;

import com.example.crm_left_service.enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    @ManyToOne
    private EventCategory category;
    private Priority priority; // low, medium, high
    private LocalDate date;
    private LocalTime time;
    private String description;

    private Boolean isRepeatEvent; // takroriymi yoki yo'q

    @ManyToOne
    private RepeatType repeatType; // daily, weekly, monthly

    @ElementCollection
    private List<String> days; // faqat weekly uchun (e.g. "Mon, Wed, Fri")

    private Boolean isEveryDay;
}
