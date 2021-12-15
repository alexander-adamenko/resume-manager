package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.*;
import com.infopulse.resumemanager.service.skillextractor.SkillExtractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SkillController {
    private final SkillExtractorService skillExtractorService;

    @GetMapping("/skills")
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDto> getAllSkills() {
        return skillExtractorService.getAllSkills();
    }

    @GetMapping("/degrees")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllDegrees() {
        return skillExtractorService.getAllDegrees();
    }

    @GetMapping("/levels")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllLevels() {
        return skillExtractorService.getAllLevels();
    }

    @GetMapping("/cities")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllCities() {
        return skillExtractorService.getAllCities();
    }

    @GetMapping("/skills-degrees-levels-cities-english-levels")
    @ResponseStatus(HttpStatus.OK)
    public SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels() {
        return skillExtractorService.getAllSkillsDegreesLevelsCitiesEnglishLevels();
    }

}
