package com.infopulse.resumemanager.dto;

import com.infopulse.resumemanager.repository.entity.enums.Level;

public record VacancySkillDto (
        Long id,
        SkillDto skill,
        Level level
)
{}
