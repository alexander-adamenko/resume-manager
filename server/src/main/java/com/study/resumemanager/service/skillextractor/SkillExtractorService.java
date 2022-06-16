package com.study.resumemanager.service.skillextractor;

import com.study.resumemanager.dto.SkillDto;
import com.study.resumemanager.dto.SkillsDegreesLevelsCitiesEnglishLevelsDto;

import java.util.List;

public interface SkillExtractorService {
    List<SkillDto> getAllSkills();
    List<String> getAllDegrees();
    List<String> getAllLevels();
    List<String> getAllCities();
    SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels();
}
