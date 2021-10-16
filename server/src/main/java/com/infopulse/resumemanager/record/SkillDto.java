package com.infopulse.resumemanager.record;

import javax.validation.constraints.NotBlank;

public record SkillDto(
        @NotBlank
        String name,
        String level
) { }
