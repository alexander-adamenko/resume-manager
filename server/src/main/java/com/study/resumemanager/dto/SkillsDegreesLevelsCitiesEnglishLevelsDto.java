package com.study.resumemanager.dto;

import java.util.List;

public record SkillsDegreesLevelsCitiesEnglishLevelsDto(
        List<SkillDto> skills,
        List<String> degrees,
        List<String> levels,
        List<String> cities,
        List<String> englishLevels
) {
}
