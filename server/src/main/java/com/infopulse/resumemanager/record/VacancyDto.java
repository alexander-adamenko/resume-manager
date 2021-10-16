package com.infopulse.resumemanager.record;

import com.infopulse.resumemanager.repository.entity.Skill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record VacancyDto(
        @NotBlank
        String positionTitle,
        Boolean needVerifiedApplicants,
        @PositiveOrZero
        Integer minimumYearsOfExperience,
        String degree,
        String location,
        Set<Skill> skills
) { }
