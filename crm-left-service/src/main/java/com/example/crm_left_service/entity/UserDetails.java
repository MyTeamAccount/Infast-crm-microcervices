package com.example.crm_left_service.entity;

import com.example.crm_left_service.enums.Gender;
import com.example.crm_left_service.enums.PositionLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails{

    private String firstName;

    private String lastName;

    private String position;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PositionLevel positionLevel;

    private  String company;

    @OneToOne
    private  Location location;

    private String phoneNumber;

    private  String skype;

    @OneToMany(mappedBy = "user")
    private List<Vacation> vacations;

    private String photoPath;

}
