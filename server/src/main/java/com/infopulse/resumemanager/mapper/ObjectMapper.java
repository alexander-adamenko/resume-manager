package com.infopulse.resumemanager.mapper;

import com.infopulse.resumemanager.dto.*;
import com.infopulse.resumemanager.repository.entity.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper
public interface ObjectMapper {
    CandidateDto candidateToCandidateDto(Candidate candidate);
    Candidate candidateDtoToCandidate(CandidateDto candidateDto);
    CandidateSkillDto candidateSkillToCandidateSkillDto(CandidateSkill candidateSkill);
    CandidateSkill candidateSkillDtoToCandidateSkill(CandidateSkillDto candidateSkillDto);
    FeedbackDto feedbackToFeedbackDto(Feedback feedback);
    SkillDto skillToSkillDto(Skill skill);
    Skill skillDtoToSkill(SkillDto skillDto);
    UserDto userToUserDto(User user);
    UserFullDto userToUserFullDto(User user);
    VacancyDto vacancyToVacancyDto(Vacancy vacancy);
    Vacancy vacancyDtoToVacancy(VacancyDto vacancy);
    UserFullDto userDtoToUserFullDto(UserDto userDto, Set<CandidateDto> candidates);
    User userDtoToUser(UserDto userDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget User user);
    RoleDto roleToRoleDto(Role role);
}
