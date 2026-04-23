package com.groupsix.projecttracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "risks")
public class Risk {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String risk;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
