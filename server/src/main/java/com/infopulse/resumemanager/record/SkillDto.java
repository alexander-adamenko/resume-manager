package com.infopulse.resumemanager.record;

import jakarta.validation.constraints.NotBlank;

public record SkillDto(
        @NotBlank
        String name,
        String level
) { }
