package com.infopulse.resumemanager.service.resumeParsingService.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.CandidateSkillDto;
import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import com.infopulse.resumemanager.repository.entity.enums.Level;
import com.infopulse.resumemanager.repository.entity.enums.Degree;

import java.util.*;

public class ExpandCandidateToCandidateDtoMapper {
    public CandidateDto map(ExtendedCandidate extendedCandidate){
        String email = extendedCandidate.getEmail();
        String phone = extendedCandidate.getPhone();
        String degreeFromEdu = getDegreeFromEdu(extendedCandidate.getEducation());
        String aboutMe = extendedCandidate.getAboutMe();
        String filepath = extendedCandidate.getFilePath();
        Set<CandidateSkillDto> candidateSkillSet = getCandidateSkillSet(extendedCandidate.getSkills());

        return new CandidateDto(null, email, phone, degreeFromEdu, aboutMe, filepath, candidateSkillSet);
    }

    private String getDegreeFromEdu(String education){
        if (education == null) return null;
        return  Arrays.stream(Degree.values())
                .filter(degree -> education.toUpperCase().contains(degree.toString()))
                .findAny()
                .map(Enum::toString)
                .orElse(null);
    }

    private Set<CandidateSkillDto> getCandidateSkillSet(List<Map<String, String>> skills){
        Set<CandidateSkillDto> resultSkillSet = new HashSet<>();
        List<String> allSkills = alignSeparatelyValuesOfMaps(skills);
        for (String skill : allSkills) {
            String skillName = skill.trim().toLowerCase();
            if(!skillName.isBlank()) {
                var skillDto = new SkillDto(skillName);
                resultSkillSet.add(new CandidateSkillDto(skillDto, Level.DEFAULT));
            }
        }
        return resultSkillSet;
    }
    private List<String> alignSeparatelyValuesOfMaps(List<Map<String, String>> skills){
        List<String> result = new ArrayList<>();
        for (Map<String, String> skill : skills) {
            for (String value : skill.values()) {
                String cleanValue = value.replaceAll("\t", ",").replaceAll("\n", "");
                result.addAll(Arrays.asList(cleanValue.split(",")));
            }
        }
        return result;
    }

}
