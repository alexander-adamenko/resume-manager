package com.infopulse.resumemanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

public record VacancyDto(
        Long id,
        @NotBlank
        String positionTitle,
        Boolean isActive,
        @PositiveOrZero
        Integer minimumYearsOfExperience,
        String degree,
        String location,
        String description,
        Set<VacancySkillDto> vacancySkills
) { }
