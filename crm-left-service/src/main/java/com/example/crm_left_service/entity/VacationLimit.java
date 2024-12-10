package com.example.crm_left_service.entity;

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
public class VacationLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer vacationLimit;
    Integer sickLeaveLimit;
    Integer workRemotelyLimit;

    Integer countVacation;
    Integer countSickLeave;
    Integer countWorkRemotely;

    @OneToOne
    User user;
}
