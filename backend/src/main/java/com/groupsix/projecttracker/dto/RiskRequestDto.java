package com.groupsix.projecttracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RiskRequestDto {
    private String risk;
    private String status;
    private UUID projectId;
}
