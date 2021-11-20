package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.SkillsDegreesLevelsCitiesDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.SkillRepository;
import com.infopulse.resumemanager.repository.entity.enums.City;
import com.infopulse.resumemanager.repository.entity.enums.Degree;
import com.infopulse.resumemanager.repository.entity.enums.Level;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class GarbageService {
    private final SkillRepository skillRepository;
    private final ObjectMapper objectMapper;

    public List<SkillDto> getAllSkills() {
        return skillRepository.findAll().stream().map(objectMapper::skillToSkillDto).toList();
    }

    public List<String> getAllDegrees() {
        return getEnumValues(Degree.class);
    }

    public List<String> getAllLevels() {
        return getEnumValues(Level.class);
    }

    public List<String> getAllCities() {
        return getEnumValues(City.class);
    }

    public SkillsDegreesLevelsCitiesDto getAllSkillsDegreesLevelsCities() {
        return new SkillsDegreesLevelsCitiesDto(
                getAllSkills(),
                getAllDegrees(),
                getAllLevels(),
                getAllCities()
        );
    }

    private List<String> getEnumValues(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toList();
    }
}
