package com.infopulse.resumemanager.dto;

import com.infopulse.resumemanager.dto.validation.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public record CandidateDto(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Email '${validatedValue}' should be valid")
        String email,
        @Phone
        String phone,
        String degree,
        String aboutMe,
        @NotBlank(message = "FilePath cannot be blank")
        String filePath,
        Set<CandidateSkillDto> candidateSkillSet
) { }
