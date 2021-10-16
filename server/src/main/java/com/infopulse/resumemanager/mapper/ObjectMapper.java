package com.infopulse.resumemanager.mapper;

import com.infopulse.resumemanager.record.*;
import com.infopulse.resumemanager.repository.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ObjectMapper {
    CandidateDto candidateToCandidateDto(Candidate candidate);
    FeedbackDto feedbackToFeedbackDto(Feedback feedback);
    SkillDto skillToSkillDto(Skill skill);
    UserDto userToUserDto(User user);
    UserFullDto userToUserFullDto(User user);
    VacancyDto vacancyToVacancyDto(Vacancy vacancy);
    UserFullDto toUserFullDto(UserDto userDto, Set<CandidateDto> candidates);

}
