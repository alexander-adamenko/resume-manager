package com.infopulse.resumemanager.dto;

import com.infopulse.resumemanager.repository.entity.Skill;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

public record VacancyDto(
        @NotBlank
        String positionTitle,
        Boolean needVerifiedApplicants,
        @PositiveOrZero
        Integer minimumYearsOfExperience,
        String degree,
        String location,
        Set<VacancySkillDto> vacancySkillSet
) { }
