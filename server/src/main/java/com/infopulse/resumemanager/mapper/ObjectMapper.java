package com.infopulse.resumemanager.mapper;

import com.infopulse.resumemanager.dto.*;
import com.infopulse.resumemanager.repository.entity.*;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper/*(componentModel = "spring")*/
public interface ObjectMapper {
    CandidateDto candidateToCandidateDto(Candidate candidate);
    FeedbackDto feedbackToFeedbackDto(Feedback feedback);

    UserFullDto userToUserFullDto(User user);
    UserFullDto userDtoToUserFullDto(UserDto userDto, Set<CandidateDto> candidates);

    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);

    VacancyDto vacancyToVacancyDto(Vacancy vacancy);
    Vacancy vacancyDtoToVacancy(VacancyDto vacancyDto);

    VacancySkill vacancySkillDtoToVacancySkill(VacancySkillDto vacancySkillDto);
    VacancySkillDto VacancySkillToVacancySkillDto(VacancySkill vacancySkill);

    SkillDto skillToSkillDto(Skill skill);
    Skill skillDtoToSkill(SkillDto skillDto);


}
