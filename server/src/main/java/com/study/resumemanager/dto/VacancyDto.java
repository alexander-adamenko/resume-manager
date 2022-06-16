package com.study.resumemanager.dto;

import com.study.resumemanager.repository.entity.enums.City;
import com.study.resumemanager.repository.entity.enums.Degree;
import com.study.resumemanager.repository.entity.enums.EnglishLevel;

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
        Degree degree,
        City location,
        EnglishLevel englishLevel,
        String description,
        Set<VacancySkillDto> vacancySkills
) { }
