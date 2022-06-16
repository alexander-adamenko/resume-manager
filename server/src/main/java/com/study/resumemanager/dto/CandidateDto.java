package com.study.resumemanager.dto;

import com.study.resumemanager.dto.validation.Phone;
import com.study.resumemanager.repository.entity.enums.City;
import com.study.resumemanager.repository.entity.enums.Degree;
import com.study.resumemanager.repository.entity.enums.EnglishLevel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public record CandidateDto(
        Long id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Email '${validatedValue}' should be valid")
        String email,
        @Phone
        String phone,
        Degree degree,
        EnglishLevel englishLevel,
        City location,
        Integer yearsOfExperience,
        String aboutMe,
        @NotBlank(message = "FilePath cannot be blank")
        String filePath,
        Set<CandidateSkillDto> candidateSkills
) { }
