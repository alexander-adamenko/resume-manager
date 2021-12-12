package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.SkillsDegreesLevelsCitiesEnglishLevelsDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.entity.Vacancy;
import com.infopulse.resumemanager.service.GarbageService;
import com.infopulse.resumemanager.service.emailsender.EmailSenderService;
import com.infopulse.resumemanager.service.matcher.CandidatesMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GarbageController {
    private final GarbageService garbageService;
    private final EmailSenderService emailSenderService;
    private final CandidatesMatcher candidatesMatcher;

    @GetMapping("/skills")
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDto> getAllSkills() {
        return garbageService.getAllSkills();
    }

    @GetMapping("/degrees")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllDegrees() {
        return garbageService.getAllDegrees();
    }

    @GetMapping("/levels")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllLevels() {
        return garbageService.getAllLevels();
    }

    @GetMapping("/cities")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllCities() {
        return garbageService.getAllCities();
    }

    @GetMapping("/skills-degrees-levels-cities-english-levels")
    @ResponseStatus(HttpStatus.OK)
    public SkillsDegreesLevelsCitiesEnglishLevelsDto getAllSkillsDegreesLevelsCitiesEnglishLevels() {
        return garbageService.getAllSkillsDegreesLevelsCitiesEnglishLevels();
    }

    @GetMapping("/send-email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail() {
        emailSenderService.sendInviteLetter(Collections.emptyList(), new Vacancy());
    }

    @GetMapping("/match-test")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDto> matchCandidatesTest(@RequestBody @Valid VacancyDto vacancyDto) {
        return candidatesMatcher.matchCandidates(vacancyDto);
    }

    @GetMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDto> matchCandidates(@RequestParam Long id) {
        return candidatesMatcher.matchCandidates(id);
    }
}
