package com.infopulse.resumemanager.dto;

import javax.validation.constraints.NotBlank;

public record SkillDto(
        Long id,
        @NotBlank
        String name

) { }
