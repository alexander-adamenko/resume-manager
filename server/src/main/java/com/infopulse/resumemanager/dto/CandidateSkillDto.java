package com.infopulse.resumemanager.dto;

import com.infopulse.resumemanager.repository.entity.enums.Level;

public record CandidateSkillDto (
    SkillDto skillDto,
    Level level
){}
