package com.groupsix.projecttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EffortDto {
    private UUID id;
    private String memberName;
    private String category;
    private double hours;
    private String entryType;
    private LocalDate entryDate;
    private UUID projectId;
}