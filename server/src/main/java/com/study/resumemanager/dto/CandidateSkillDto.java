package com.study.resumemanager.dto;

import com.study.resumemanager.repository.entity.enums.Level;

public record CandidateSkillDto (
    SkillDto skill,
    Level level
){}
