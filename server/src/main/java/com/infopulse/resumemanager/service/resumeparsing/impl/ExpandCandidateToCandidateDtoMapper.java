package com.infopulse.resumemanager.service.resumeparsing.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.CandidateSkillDto;
import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import com.infopulse.resumemanager.repository.entity.enums.City;
import com.infopulse.resumemanager.repository.entity.enums.EnglishLevel;
import com.infopulse.resumemanager.repository.entity.enums.Level;
import com.infopulse.resumemanager.repository.entity.enums.Degree;

import java.util.*;

public class ExpandCandidateToCandidateDtoMapper {
    public CandidateDto map(ExtendedCandidate extendedCandidate){
        String email = extendedCandidate.getEmail();
        String phone = extendedCandidate.getPhone();
        Degree degreeFromEdu = null;
        Optional<Degree> degreeEdu = getDegreeFromEdu(extendedCandidate.getEducation());
        if(degreeEdu.isPresent()){
            degreeFromEdu = degreeEdu.get();
        }
        String aboutMe = extendedCandidate.getAboutMe();
        String filepath = extendedCandidate.getFilePath();
        Set<CandidateSkillDto> candidateSkillSet = getCandidateSkills(extendedCandidate.getSkills());
        EnglishLevel englishLevel = getEnglishLevelFromSkills(candidateSkillSet);
        City city = extendedCandidate.getCity();
        return new CandidateDto(null, null, email, phone, degreeFromEdu, englishLevel, city, 0, aboutMe, filepath, candidateSkillSet);
    }

    private Optional<Degree> getDegreeFromEdu(String education){
        if (education == null) return Optional.empty();
        return  Arrays.stream(Degree.values())
                .filter(degree -> education.toLowerCase().contains(degree.toString().toLowerCase()))
                .findAny();
    }

    private Set<CandidateSkillDto> getCandidateSkills(List<Map<String, String>> skills){
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

    private EnglishLevel getEnglishLevelFromSkills(Set<CandidateSkillDto> skills){
        Iterator<CandidateSkillDto> iterator = skills.iterator();
        while (iterator.hasNext()) {
            CandidateSkillDto next = iterator.next();
            Optional<EnglishLevel> any = Arrays.stream(EnglishLevel.values())
                    .filter(englishLevel -> next.skill().name().toLowerCase()
                            .contains(englishLevel.toString().toLowerCase()) &&
                            next.skill().name().toLowerCase()
                            .contains("english"))
                    .findAny();
            if(any.isPresent()) {
                iterator.remove();
                return any.get();
            }
        }

        return null;
    }


}
