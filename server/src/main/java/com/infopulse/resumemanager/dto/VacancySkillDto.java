package com.infopulse.resumemanager.dto;

import com.infopulse.resumemanager.repository.entity.enums.Level;

public record VacancySkillDto (
        SkillDto skillDto,
        Level level
)
{}
