package com.groupsix.projecttracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestDto {
    private String name;
    private String description;
    private String owner;
    private List<String> members;
}
