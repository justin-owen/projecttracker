package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.RiskDto;
import com.groupsix.projecttracker.dto.RiskRequestDto;
import com.groupsix.projecttracker.entity.Risk;
import com.groupsix.projecttracker.mapper.RiskMapper;
import com.groupsix.projecttracker.repository.ProjectRepository;
import com.groupsix.projecttracker.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final ProjectRepository projectRepository;
    private final RiskRepository riskRepository;
    private final RiskMapper riskMapper;

    public List<RiskDto> listAllRisksByProject(UUID projectId){
        return riskRepository.findAllByProjectId(projectId)
                .stream()
                .map(riskMapper::toDto)
                .toList();
    }

    public RiskDto createRisk(RiskRequestDto riskRequest){
        Risk risk = new Risk();
        risk.setRisk(riskRequest.getRisk());
        risk.setStatus(riskRequest.getStatus());
        try {
            risk.setProject(projectRepository.findById(riskRequest.getProjectId()).orElseThrow());
            Risk savedRisk = riskRepository.save(risk);
            return riskMapper.toDto(savedRisk);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Failed creating risk.");
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Could not find project.");
        }
    }

    public RiskDto editRisk(UUID riskId, RiskRequestDto riskRequest){
        try {
            Risk risk = riskRepository.findById(riskId).orElseThrow();
            risk.setRisk(riskRequest.getRisk());
            risk.setStatus(riskRequest.getStatus());
            Risk savedRisk = riskRepository.save(risk);
            return riskMapper.toDto(savedRisk);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Finding Risk failed.");
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Failed editing risk.");
        }
    }

    public void deleteRisk(UUID riskId){
        try {
            Risk risk = riskRepository.findById(riskId).orElseThrow();
            riskRepository.delete(risk);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Finding Risk failed.");
        } catch (Exception e){
            throw new RuntimeException("Failed deleting risk.");
        }
    }
}
