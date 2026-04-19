package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.RiskDto;
import com.groupsix.projecttracker.mapper.RiskMapper;
import com.groupsix.projecttracker.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;
    private final RiskMapper riskMapper;

    public List<RiskDto> listAllRisksByProject(UUID projectId){
        return riskRepository.findAllByProjectId(projectId)
                .stream()
                .map(riskMapper::toDto)
                .toList();
    }
}
