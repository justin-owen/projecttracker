package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.EffortDto;
import com.groupsix.projecttracker.service.EffortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/efforts")
@RequiredArgsConstructor
public class EffortController {

    private final EffortService effortService;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<EffortDto>> getAllEffortsByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(effortService.getAllEffortsByProject(projectId));
    }

    //creates a new effort
    @PostMapping("/create")
    public ResponseEntity<EffortDto> createEffort(@RequestBody EffortDto effortDto) {
        try {
            EffortDto effort = effortService.createEffort(effortDto);
            return ResponseEntity.ok(effort);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //edits existing effort
    @PutMapping("/{effortId}")
    public ResponseEntity<EffortDto> editEffort(
            @PathVariable UUID effortId,
            @RequestBody EffortDto effortDto
    ) {
        try {
            EffortDto effort = effortService.editEffort(effortId, effortDto);
            return ResponseEntity.ok(effort);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //deletes existing effort
    @DeleteMapping("/{effortId}")
    public ResponseEntity<EffortDto> deleteEffort(@PathVariable UUID effortId) {
        try {
            effortService.deleteEffort(effortId);
            return ResponseEntity.ok().body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //Get the total of effort hours for a project
    @GetMapping("/{projectId}/total")
    public ResponseEntity<Double> getTotalProjectHours(@PathVariable UUID projectId) {
        return ResponseEntity.ok(effortService.getTotalProjectHours(projectId));
    }
    //Gets summary of total hours of effort for a project
    @GetMapping("/{projectId}/summary")
    public ResponseEntity<Map<String, Double>> getProjectEffortSummary(@PathVariable UUID projectId) {
        return ResponseEntity.ok(effortService.getProjectEffortSummary(projectId));
    }
}