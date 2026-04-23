package com.groupsix.projecttracker.mapper;

import com.groupsix.projecttracker.dto.RiskDto;
import com.groupsix.projecttracker.entity.Risk;
import org.springframework.stereotype.Component;

@Component
public class RiskMapper {
    public RiskDto toDto(Risk risk){
        if (risk == null) return null;

        return RiskDto.builder()
                .risk(risk.getRisk())
                .status(risk.getStatus())
                .build();
    }
}
