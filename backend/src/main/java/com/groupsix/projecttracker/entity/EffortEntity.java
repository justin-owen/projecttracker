package com.groupsix.projecttracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "efforts")
public class EffortEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private double hours;

    @Column(nullable = false)
    private String entryType; // DAILY or WEEKLY

    @Column(nullable = false)
    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}