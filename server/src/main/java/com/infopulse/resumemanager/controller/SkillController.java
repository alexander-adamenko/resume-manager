package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.SkillsDegreesLevelsCitiesEnglishLevelsDto;
import com.infopulse.resumemanager.service.skillextractor.SkillExtractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SkillController {
    private final SkillExtractorService skillExtractorService;

    @GetMapping("/skills")
    public List<SkillDto> getAllSkills() {
        return skillExtractorService.getAllSkills();
    }

    @GetMapping("/degrees")
    public List<String> getAllDegrees() {
        return skillExtractorService.getAllDegrees();
    }

    @GetMapping("/levels")
    public List<String> getAllLevels() {
        return skillExtractorService.getAllLevels();
    }

    @GetMapping("/cities")
    public List<String> getAllCities() {
        return skillExtractorService.getAllCities();
    }

    @GetMapping("/skills-degrees-levels-cities-english-levels")
    public SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels() {
        return skillExtractorService.getAllSkillsDegreesLevelsCitiesEnglishLevels();
    }

}
