package com.study.resumemanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.util.Set;

public record UserFullDto(
        @Size(
                min = 4,
                max = 20,
                message = "The username '${validatedValue}' must be between {min} and {max} characters long."
        )
        String username,
        @Size(
                min = 8,
                max = 20,
                message = "The password must be between {min} and {max} characters long."
        )
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        String firstName,
        String lastName,
        Set<RoleDto> roles,
        Set<CandidateDto> candidates,
        Set<VacancyDto> vacancies,
        Set<FeedbackDto> feedbacks
) { }
