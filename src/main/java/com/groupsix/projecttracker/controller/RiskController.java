package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.RiskDto;
import com.groupsix.projecttracker.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/risks")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<RiskDto>> getAllRisksByProject(
            @PathVariable UUID projectId){
        return ResponseEntity.ok(riskService.listAllRisksByProject(projectId));
    }
}
