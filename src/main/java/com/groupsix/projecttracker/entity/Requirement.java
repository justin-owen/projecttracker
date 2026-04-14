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
@Table(name = "requirements")
public class Requirement {

    @Id
    private UUID id;

    private String type;

    private String requirement;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
