package com.groupsix.projecttracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequirementRequestDto {
    private String requirement;
    private String type;
    private UUID projectId;
}
