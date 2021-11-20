package com.infopulse.resumemanager.mapper;

import com.infopulse.resumemanager.dto.*;
import com.infopulse.resumemanager.repository.entity.*;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface ObjectMapper {
    CandidateDto candidateToCandidateDto(Candidate candidate);
    FeedbackDto feedbackToFeedbackDto(Feedback feedback);
    SkillDto skillToSkillDto(Skill skill);
    UserDto userToUserDto(User user);
    UserFullDto userToUserFullDto(User user);
    VacancyDto vacancyToVacancyDto(Vacancy vacancy);
    Vacancy vacancyDtoToVacancy(VacancyDto vacancy);
    UserFullDto userDtoToUserFullDto(UserDto userDto, Set<CandidateDto> candidates);
    User userDtoToUser(UserDto userDto);
}
