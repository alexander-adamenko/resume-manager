package com.infopulse.resumemanager.dto;

import javax.validation.constraints.NotBlank;

public record SkillDto(
        @NotBlank
        String name

) { }
