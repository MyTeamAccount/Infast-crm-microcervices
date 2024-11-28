package com.example.crm_left_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User teamLeaderId;

    @ManyToOne
    @JoinColumn(name = "team_type_id")
    private TeamType teamType;

    @ManyToMany
    private List<User> teamMembers;
}

