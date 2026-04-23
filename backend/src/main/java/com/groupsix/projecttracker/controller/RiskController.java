package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.dto.RequirementRequestDto;
import com.groupsix.projecttracker.dto.RiskDto;
import com.groupsix.projecttracker.dto.RiskRequestDto;
import com.groupsix.projecttracker.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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

    @PostMapping("/create")
    public ResponseEntity<RiskDto> createRisk(
            @RequestBody RiskRequestDto riskRequest){
        try {
            RiskDto risk = riskService.createRisk(riskRequest);
            return ResponseEntity.ok(risk);
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{riskId}")
    public ResponseEntity<RiskDto> editRisk(
            @PathVariable UUID riskId,
            @RequestBody RiskRequestDto riskRequest
    ){
        try {
            RiskDto risk = riskService.editRisk(riskId, riskRequest);
            return ResponseEntity.ok(risk);
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{riskId}")
    public ResponseEntity<RiskDto> deleteRisk(
            @PathVariable UUID riskId
    ){
        try {
            riskService.deleteRisk(riskId);
            return ResponseEntity.ok().body(null);
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
