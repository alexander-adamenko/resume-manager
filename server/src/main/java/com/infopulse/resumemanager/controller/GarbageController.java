package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.SkillsDegreesLevelsCitiesDto;
import com.infopulse.resumemanager.repository.entity.Vacancy;
import com.infopulse.resumemanager.service.GarbageService;
import com.infopulse.resumemanager.service.emailsender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GarbageController {
    private final GarbageService garbageService;
    private final EmailSenderService emailSenderService;

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

    @GetMapping("/skills-degrees-levels-cities")
    @ResponseStatus(HttpStatus.OK)
    public SkillsDegreesLevelsCitiesDto getAllSkillsDegreesLevelsCities() {
        return garbageService.getAllSkillsDegreesLevelsCities();
    }

    @GetMapping("/send-email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail() {
        emailSenderService.sendInviteLetter(Collections.emptyList(), new Vacancy());
    }

}
