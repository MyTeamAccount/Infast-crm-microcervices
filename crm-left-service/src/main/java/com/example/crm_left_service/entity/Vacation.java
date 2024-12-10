package com.example.crm_left_service.entity;

import com.example.crm_left_service.enums.VacationPermission;
import com.example.crm_left_service.enums.VocationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    User user;

    @Enumerated(EnumType.STRING)
    VocationType vocationType;

    String startDate;
    String endDate;

    String comment;

    VacationPermission vacationPermission;
}
