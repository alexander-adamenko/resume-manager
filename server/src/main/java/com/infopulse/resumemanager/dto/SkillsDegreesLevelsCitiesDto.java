package com.infopulse.resumemanager.dto;

import java.util.List;

public record SkillsDegreesLevelsCitiesDto(
        List<SkillDto> skills,
        List<String> degrees,
        List<String> levels,
        List<String> cities
) {
}
