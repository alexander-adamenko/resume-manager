package com.infopulse.resumemanager.service.skillextractor;

import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.SkillsDegreesLevelsCitiesEnglishLevelsDto;

import java.util.List;

public interface SkillExtractorService {
    List<SkillDto> getAllSkills();
    List<String> getAllDegrees();
    List<String> getAllLevels();
    List<String> getAllCities();
    SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels();
}
