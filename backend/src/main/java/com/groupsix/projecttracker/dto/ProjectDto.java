package com.groupsix.projecttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String name;
    private String description;
    private String owner;
    private List<String> members;
    private UUID id;
}
