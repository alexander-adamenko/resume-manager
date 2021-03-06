package com.study.resumemanager.service.skillextractor;

import com.study.resumemanager.dto.SkillDto;
import com.study.resumemanager.dto.SkillsDegreesLevelsCitiesEnglishLevelsDto;
import com.study.resumemanager.mapper.ObjectMapper;
import com.study.resumemanager.repository.SkillRepository;
import com.study.resumemanager.repository.entity.enums.City;
import com.study.resumemanager.repository.entity.enums.Degree;
import com.study.resumemanager.repository.entity.enums.EnglishLevel;
import com.study.resumemanager.repository.entity.enums.Level;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SkillExtractorServiceImpl implements SkillExtractorService {
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

    private List<String> getAllEnglishLevels() {
        return getEnumValues(EnglishLevel.class);
    }

    public SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels() {
        return new SkillsDegreesLevelsCitiesEnglishLevelsDto(
                getAllSkills(),
                getAllDegrees(),
                getAllLevels(),
                getAllCities(),
                getAllEnglishLevels()
        );
    }

    private List<String> getEnumValues(Class<? extends Enum<?>> e) {
        Enum<?>[] enums = e.getEnumConstants();
        String[] names = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            names[i] = enums[i].toString();
        }
        return new ArrayList<String>(Arrays.asList(names));
    }
}
