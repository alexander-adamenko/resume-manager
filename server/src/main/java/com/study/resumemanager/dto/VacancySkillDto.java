package com.study.resumemanager.dto;

import com.study.resumemanager.repository.entity.enums.Level;

public record VacancySkillDto (
        Long id,
        SkillDto skill,
        Level level
)
{}
