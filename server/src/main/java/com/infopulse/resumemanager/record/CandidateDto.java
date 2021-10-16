package com.infopulse.resumemanager.record;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public record CandidateDto(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Email '${validatedValue}' should be valid")
        String email,
        //todo: write annotation for phone numbers
        String phone,
        String degree,
        String aboutMe,
        String filePath,
        Set<SkillDto> skills
) { }
